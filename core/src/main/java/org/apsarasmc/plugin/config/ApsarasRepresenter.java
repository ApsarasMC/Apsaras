package org.apsarasmc.plugin.config;

import org.apsarasmc.apsaras.config.Comment;
import org.apsarasmc.apsaras.config.Comments;
import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.comments.CommentLine;
import org.yaml.snakeyaml.comments.CommentType;
import org.yaml.snakeyaml.introspector.Property;
import org.yaml.snakeyaml.nodes.*;
import org.yaml.snakeyaml.representer.Representer;

import java.util.*;

public class ApsarasRepresenter extends Representer {

    public ApsarasRepresenter() {
        super();
        setPropertyUtils(new ApsarasPropertyUtils());
    }

    @Override
    protected MappingNode representJavaBean(Set<Property> propertySet, Object javaBean) {
        List<Property> properties = new ArrayList<>(propertySet);
        List<NodeTuple> value = new ArrayList<>(properties.size());
        MappingNode node = new MappingNode(Tag.MAP, value, DumperOptions.FlowStyle.AUTO);
        if(0 < properties.size()) {
            node.setBlockComments(getComments(properties.get(0)));
        }
        representedObjects.put(javaBean, node);
        DumperOptions.FlowStyle bestStyle = DumperOptions.FlowStyle.BLOCK;
        for (int i = 0; i < properties.size(); i++) {
            Property property = properties.get(i);

            Object memberValue = property.get(javaBean);
            Tag customPropertyTag = memberValue == null ? null
                    : classTags.get(memberValue.getClass());

            NodeTuple tuple = representJavaBeanProperty(javaBean, property, memberValue,
                    customPropertyTag);
            if (tuple == null) {
                continue;
            }
            if(i + 1 < properties.size()) {
                tuple.getValueNode().setEndComments(getComments(properties.get(i + 1)));
            }
            value.add(tuple);
        }
        if (defaultFlowStyle != DumperOptions.FlowStyle.AUTO) {
            node.setFlowStyle(defaultFlowStyle);
        } else {
            node.setFlowStyle(bestStyle);
        }
        return node;
    }

    protected List<CommentLine> getComments(Property property) {
        if(property == null){
            return Collections.emptyList();
        }
        LinkedList<CommentLine> commentValues = new LinkedList<>();
        Comments comments = property.getAnnotation(Comments.class);
        if(comments == null){
            Comment comment = property.getAnnotation(Comment.class);
            if(comment != null){
                commentValues.add(new CommentLine(null,null," "+comment.value(),CommentType.BLOCK));
            }
        }else{
            for (Comment comment : comments.value()) {
                commentValues.add(new CommentLine(null,null," "+comment.value(),CommentType.BLOCK));
            }
        }
        return commentValues;
    }

    @Override
    protected void checkGlobalTag(Property property, Node node, Object object) {
        // Skip primitive arrays.
        if (object.getClass().isArray() && object.getClass().getComponentType().isPrimitive()) {
            return;
        }

        Class<?>[] arguments = property.getActualTypeArguments();
        if (arguments != null) {
            if (node.getNodeId() == NodeId.sequence) {
                // apply map tag where class is the same
                Class<? extends Object> t = arguments[0];
                SequenceNode snode = (SequenceNode) node;
                Iterable<Object> memberList = Collections.EMPTY_LIST;
                if (object.getClass().isArray()) {
                    memberList = Arrays.asList((Object[]) object);
                } else if (object instanceof Iterable<?>) {
                    // list
                    memberList = (Iterable<Object>) object;
                }
                Iterator<Object> iter = memberList.iterator();
                if (iter.hasNext()) {
                    for (Node childNode : snode.getValue()) {
                        Object member = iter.next();
                        if (member != null) {
                            resetTag(t, childNode);
                        }
                    }
                }
            } else if (object instanceof Set) {
                Class<?> t = arguments[0];
                MappingNode mnode = (MappingNode) node;
                Iterator<NodeTuple> iter = mnode.getValue().iterator();
                Set<?> set = (Set<?>) object;
                for (Object member : set) {
                    NodeTuple tuple = iter.next();
                    Node keyNode = tuple.getKeyNode();
                    resetTag(t, keyNode);
                }
            } else if (object instanceof Map) { // NodeId.mapping ends-up here
                Class<?> keyType = arguments[0];
                Class<?> valueType = arguments[1];
                MappingNode mnode = (MappingNode) node;
                for (NodeTuple tuple : mnode.getValue()) {
                    resetTag(keyType, tuple.getKeyNode());
                    resetTag(valueType, tuple.getValueNode());
                }
            } else {
                // the type for collection entries cannot be
                // detected
            }
        }
    }

    private void resetTag(Class<? extends Object> type, Node node) {
        Tag tag = node.getTag();
        if (tag.matches(type)) {
            if (Enum.class.isAssignableFrom(type)) {
                node.setTag(Tag.STR);
            } else {
                node.setTag(Tag.MAP);
            }
        }
    }
}
