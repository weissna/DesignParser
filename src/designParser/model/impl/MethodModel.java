package designParser.model.impl;

import java.util.ArrayList;
import java.util.List;

import designParser.markupGen.api.IModelVisitor;
import designParser.model.api.IMethod;

public class MethodModel implements IMethod {
    private String objName;
    private String mthdName;
    private AccessLevel accessLevel;
    private String retTypeName;
    private String[] paramTypeNames;   
    private List<IMethod> methodCalls;

    public MethodModel(String objName, String mthdName, AccessLevel accessLevel,
            String retTypeName, String[] paramTypeNames) {
        this.objName = objName;
        this.mthdName = mthdName;
        this.accessLevel = accessLevel;
        this.retTypeName = retTypeName;
        this.paramTypeNames = paramTypeNames;
        this.methodCalls = new ArrayList<IMethod>();
    }
    
    @Override
    public void accept(IModelVisitor visitor) {       
        visitor.incrementCallDepth();
        visitor.previsit(this);
        visitor.visit(this);
        if (visitor.getCallDepth() <= visitor.getMaxCallDepth()) {
            for (IMethod m : methodCalls) {
                m.accept(visitor);
            }
        }
        visitor.postvisit(this);
        visitor.decrementCallDepth();
    }
    
    @Override
    public String getSignature() {
        return getSignature(mthdName, accessLevel, retTypeName, paramTypeNames);
    }
    
    @Override
    public String getAbbrevSignature() {
        return getAbbrevSignature(mthdName, paramTypeNames);
    }
    
    @Override
    public String getName() {
        return mthdName;
    }
    
    @Override
    public String getObjectName() {
        return objName;
    }
    
    @Override
    public AccessLevel getAccessLevel() {
        return accessLevel;
    }
    
    @Override
    public boolean isConstructor() {
        return objName.equals(mthdName);
    }
    
    @Override
    public void setAccessLevel(AccessLevel accessLevel) {
        this.accessLevel = accessLevel;
    }

    @Override
    public void setParamTypeNames(String[] paramTypeNames) {
        this.paramTypeNames = paramTypeNames;
    }

    @Override
    public void setReturnTypeName(String returnTypeName) {
        this.retTypeName = returnTypeName;
    }
    
    @Override
    public void putMethodCall(IMethod methodModel) {
        methodCalls.add(methodModel);
    }

    public static String getSignature(String methodName, AccessLevel accessLevel, String retTypeName, 
            String[] paramTypeNames) {
        String sig = retTypeName + " " + getAbbrevSignature(methodName, paramTypeNames);
        if (accessLevel != AccessLevel.Default && accessLevel != null) {
            sig = accessLevel.toUmlString() + " " + sig;
        }
        return sig;
    }
    
    public static String getAbbrevSignature(String methodName, String[] paramTypeNames) {
        return methodName + "(" + String.join(", ", paramTypeNames) + ")";
    }
}
