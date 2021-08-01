package org.apsarasmc.annotation;

import com.google.gson.Gson;

import javax.annotation.processing.*;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.tools.StandardLocation;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@SupportedAnnotationTypes ({
  "org.apsarasmc.apsaras.aop.Component"
})
@SupportedSourceVersion (SourceVersion.RELEASE_8)
public class RegisterComponent extends AbstractProcessor {
  @Override
  public boolean process(Set< ? extends TypeElement > annotations, RoundEnvironment roundEnv) {
    Gson gson = new Gson();
    Set< String > componentClasses = new HashSet<>();
    Filer filer = super.processingEnv.getFiler();

    try (Reader reader = filer.getResource(StandardLocation.CLASS_OUTPUT, "", "META-INF/components.json").openReader(true)) {
      componentClasses = gson.fromJson(reader, Set.class);
    } catch (IOException e) {
      //
    }
    Optional< ? extends TypeElement > elementOptional = annotations.stream().findFirst();
    if (!elementOptional.isPresent()) {
      return false;
    }
    TypeElement typeElement = elementOptional.get();

    for (Element element : roundEnv.getElementsAnnotatedWith(typeElement)) {
      componentClasses.add(
        ((TypeElement) element).getQualifiedName().toString()
      );
    }

    try (Writer writer = filer.createResource(StandardLocation.CLASS_OUTPUT, "", "META-INF/components.json").openWriter()) {
      gson.toJson(componentClasses, writer);
    } catch (IOException e) {
      e.printStackTrace();
    }
    return false;
  }
}
