package de.qaware.smartlabmonolith.api.service;

import de.qaware.smartlabapi.service.IServiceBaseUrlGetter;
import de.qaware.smartlabapi.service.gui.IGuiService;
import de.qaware.smartlabcore.miscellaneous.Property;
import de.qaware.smartlabgui.controller.GuiController;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.net.URL;

@Component
@ConditionalOnProperty(
        prefix = Property.Prefix.MODULARITY,
        name = Property.Name.MODULARITY,
        havingValue = Property.Value.Modularity.MONOLITH)
public class GuiMonolithicService implements IGuiService {

    @Component
    // TODO: String literal
    @Qualifier("guiBaseUrlGetter")
    @ConditionalOnProperty(
            prefix = Property.Prefix.MODULARITY,
            name = Property.Name.MODULARITY,
            havingValue = Property.Value.Modularity.MONOLITH)
    public static class BaseUrlGetter implements IServiceBaseUrlGetter {

        private final GuiController.BaseUrlGetter guiBaseUrlGetter;

        public BaseUrlGetter(GuiController.BaseUrlGetter guiBaseUrlGetter) {
            this.guiBaseUrlGetter = guiBaseUrlGetter;
        }

        @Override
        public URL getBaseUrl() {
            return this.guiBaseUrlGetter.getBaseUrl().getBody();
        }
    }
}
