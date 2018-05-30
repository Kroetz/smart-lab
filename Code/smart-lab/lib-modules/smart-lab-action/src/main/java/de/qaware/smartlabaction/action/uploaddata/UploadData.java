package de.qaware.smartlabaction.action.uploaddata;

import de.qaware.smartlabcommons.api.internal.service.action.IActionService;
import de.qaware.smartlabcommons.api.internal.service.delegate.IDelegateService;
import de.qaware.smartlabaction.action.generic.AbstractAction;
import de.qaware.smartlabcommons.data.action.generic.IActionArgs;
import de.qaware.smartlabcommons.data.action.generic.result.IActionResult;
import de.qaware.smartlabaction.action.generic.result.VoidActionResult;
import de.qaware.smartlabcommons.data.action.uploaddata.IUploadDataService;
import de.qaware.smartlabcommons.data.generic.IResolver;
import de.qaware.smartlabcommons.data.workgroup.IKnowledgeBaseInfo;
import de.qaware.smartlabcommons.exception.UnknownServiceException;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class UploadData extends AbstractAction<UploadData.ActionArgs, Void> {

    public static final String ACTION_ID = "upload data";
    private IResolver<String, IUploadDataService> uploadDataServiceResolver;

    public UploadData(IResolver<String, IUploadDataService> uploadDataServiceResolver) {
        super(ACTION_ID);
        this.uploadDataServiceResolver = uploadDataServiceResolver;
    }

    public Void submitCall(IActionService actionService, ActionArgs actionArgs) {
        IActionResult actionResult = actionService.executeAction(UploadData.ACTION_ID, actionArgs);
        return actionResult.getVoidValue();
    }

    @Override
    public IActionResult execute(String deviceType, IActionArgs genericActionArgs) {
        // TODO: Is never needed because there is no local execution for a web service.
        return VoidActionResult.instance();
    }

    @Override
    public IActionResult execute(IActionArgs genericActionArgs, IDelegateService delegateService) {
        // Every action can only handle its own specific argument type.
        // TODO: Move this call somewhere else so that this method always gets the right action args type (parameterized?)
        ActionArgs actionArgs = convertToSpecific(UploadData.ActionArgs.class, genericActionArgs);
        IUploadDataService uploadDataService = this.uploadDataServiceResolver
                .resolve(actionArgs.getKnowledgeBaseInfo().getServiceId())
                .orElseThrow(UnknownServiceException::new);
        uploadDataService.upload(actionArgs.getKnowledgeBaseInfo(), actionArgs.getDataToUpload());
        return VoidActionResult.instance();
    }

    @Data
    @RequiredArgsConstructor(staticName = "of")     // TODO: Eliminate string literal
    @NoArgsConstructor // TODO: Really necessary for objects being able to serialize/deserialize?
    public static class ActionArgs implements IActionArgs {

        @NonNull
        private IKnowledgeBaseInfo knowledgeBaseInfo;

        @NonNull
        private String dataToUpload;
    }
}
