package eu.goldenkoopa.stockmanagementsystem.utils;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.List;
import org.springframework.security.access.annotation.Secured;

public class AnnotationScanner {
  public static List<String> scan() throws Exception {
    String packageName = "eu.goldenkoopa.stockmanagementsystem.controllers.v1";
    List<Class<?>> classes = getClasses(packageName);
    List<String> privileges = new ArrayList<>();

    for (Class<?> clazz : classes) {
      if (clazz.isAnnotationPresent(Secured.class)) {
        Secured annotation = clazz.getAnnotation(Secured.class);
        Arrays.asList(annotation.value()).forEach(privilege -> {
          if (!privileges.contains(privilege)) {
            privileges.add(privilege);
          }
        });
        // System.out.println("Class: " + clazz.getName() +
        // ", Value: " + Arrays.toString(annotation.value()));
      }
      for (Method method : clazz.getDeclaredMethods()) {
        if (method.isAnnotationPresent(Secured.class)) {
          Secured annotation = method.getAnnotation(Secured.class);
          Arrays.asList(annotation.value()).forEach(privilege -> {
            if (!privileges.contains(privilege)) {
              privileges.add(privilege);
            }
          });
          // System.out.println(
          // "Class: " + clazz.getName() + ", Method: " + method.getName() +
          // ", Value: " + Arrays.toString(annotation.value()));
        }
      }
    }
    System.out.println("Privileges found: " + privileges);
    return privileges;
  }

  // Method to scan classes from a package
  private static List<Class<?>> getClasses(String packageName)
      throws IOException, ClassNotFoundException {
    List<Class<?>> classes = new ArrayList<>();
    String path = packageName.replace('.', '/');
    ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
    Enumeration<URL> resources = classLoader.getResources(path);

    while (resources.hasMoreElements()) {
      File directory = new File(resources.nextElement().getFile());
      if (directory.exists()) {
        for (String file : directory.list()) {
          if (file.endsWith(".class")) {
            String className = packageName + '.' + file.substring(0, file.length() - 6);
            classes.add(Class.forName(className));
          }
        }
      }
    }
    return classes;
  }
}
