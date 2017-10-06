package org.sonarsource.shooter.web;

import org.sonar.api.web.page.Context;
import org.sonar.api.web.page.Page;
import org.sonar.api.web.page.Page.Scope;
import org.sonar.api.web.page.PageDefinition;

public class ShooterPluginPageDefinition implements PageDefinition {

  @Override
  public void define(Context context) {
    context
      .addPage(Page.builder("shooterplugin/loader")
        .setName("Sonar Shooter !")
        .setScope(Scope.COMPONENT).setComponentQualifiers(Page.Qualifier.PROJECT).build());
  }
}
