package designParser.model.api;

import designParser.model.impl.AccessLevel;

public interface IMethod extends IModelComponent {
    public String getObjectName();
    public AccessLevel getAccessLevel();
    public boolean isConstructor();
    public String getSignature();
    public String getAbbrevSignature();
    public void setAccessLevel(AccessLevel accessLevel);
    public void setParamTypeNames(String[] paramTypeNames);
    public void setReturnTypeName(String returnTypeName);
    public void putMethodCall(IMethod methodModel);
}