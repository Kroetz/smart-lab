package de.qaware.smartlabmonolith.api.service;

import de.qaware.smartlabapi.service.IServiceBaseUrlGetter;
import de.qaware.smartlabapi.service.gui.IGuiService;
import de.qaware.smartlabcore.miscellaneous.Property;
import de.qaware.smartlabgui.controller.GuiController;
import de.qaware.smartlabmonolith.api.service.url.AbstractMonolithicBaseUrlGetter;
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
    @Qualifier("guiServiceBaseUrlGetter")
    @ConditionalOnProperty(
            prefix = Property.Prefix.MODULARITY,
            name = Property.Name.MODULARITY,
            havingValue = Property.Value.Modularity.MONOLITH)
    public static class BaseUrlGetter extends AbstractMonolithicBaseUrlGetter {

        public BaseUrlGetter(GuiController.BaseUrlController guiServiceBaseUrlController) {
            super(guiServiceBaseUrlController);
        }
    }
}
