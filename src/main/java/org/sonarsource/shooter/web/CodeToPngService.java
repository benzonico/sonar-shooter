package org.sonarsource.shooter.web;

import org.sonar.api.server.ws.WebService;

public class CodeToPngService implements WebService {
  @Override
  public void define(Context context) {
    NewController controller = context.createController("codetopng");
    NewAction action = controller.createAction("convert");
    action.createParam("fileKey");
    action.setHandler((req, rep) -> {
      rep.setHeader("Content-type", "image/png");
      Converter.convert(req.getParam("fileKey").getValue(), rep.stream().output());
    });
    controller.done();
  }

}
