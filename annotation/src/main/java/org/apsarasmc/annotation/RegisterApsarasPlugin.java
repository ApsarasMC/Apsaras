package org.apsarasmc.annotation;

import com.google.common.base.Strings;
import com.google.gson.Gson;
import org.apsarasmc.apsaras.aop.ApsarasPlugin;
import org.apsarasmc.apsaras.aop.Dependency;
import org.apsarasmc.plugin.plugin.ImplPluginDepend;
import org.apsarasmc.plugin.plugin.ImplPluginMeta;
import org.apsarasmc.plugin.plugin.ImplPluginUrls;

import javax.annotation.processing.*;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.tools.StandardLocation;
import java.io.IOException;
import java.io.Writer;
import java.util.Optional;
import java.util.Set;

@SupportedAnnotationTypes ({
  "org.apsarasmc.apsaras.aop.ApsarasPlugin"
})
@SupportedSourceVersion (SourceVersion.RELEASE_8)
public class RegisterApsarasPlugin extends AbstractProcessor {
  @Override
  public boolean process(Set< ? extends TypeElement > annotations, RoundEnvironment roundEnv) {
    Gson gson = new Gson();
    Filer filer = super.processingEnv.getFiler();

    Optional< ? extends TypeElement > typeElement = annotations.stream().findFirst();
    if (!typeElement.isPresent()) {
      return false;
    }
    Optional< ? extends Element > elementOptional = roundEnv.getElementsAnnotatedWith(typeElement.get()).stream().findFirst();
    if (!elementOptional.isPresent()) {
      return false;
    }
    ApsarasPlugin apsarasPlugin = elementOptional.get().getAnnotation(ApsarasPlugin.class);

    try (Writer writer = filer.createResource(StandardLocation.CLASS_OUTPUT, "", "META-INF/apsaras.json").openWriter()) {
      ImplPluginMeta.Builder builder = new ImplPluginMeta.Builder();
      builder.name(apsarasPlugin.name())
        .version(Strings.emptyToNull(apsarasPlugin.version()))
        .describe(Strings.emptyToNull(apsarasPlugin.describe()))
        .main(((TypeElement) elementOptional.get()).getQualifiedName().toString());
      for (Dependency dependency : apsarasPlugin.dependency()) {
        builder.depend(new ImplPluginDepend.Builder().type(dependency.type()).name(dependency.name()));
      }
      builder.urls(
        new ImplPluginUrls.Builder()
          .home(Strings.emptyToNull(apsarasPlugin.urls().home()))
          .issue(Strings.emptyToNull(apsarasPlugin.urls().issue()))
      );
      gson.toJson(builder.build(), writer);
    } catch (IOException e) {
      e.printStackTrace();
    }
    return false;
  }
}
