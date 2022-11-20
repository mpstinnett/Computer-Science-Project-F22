package com.group12.degreeaudit.Administration;

public class JSONDegreeWrapper {
    private JSONDegree jsonDegree;

    public JSONDegreeWrapper() 
    {
        jsonDegree = new JSONDegree();
    }

    public JSONDegreeWrapper(String degreeName, String coreRequirementAmount, String coreGPARequirement, 
            boolean coreReplaceHighestAttempt, boolean coreAllowSeventhElective, String electiveRequirementAmount,
            String electiveGPARequirement, boolean electiveReplaceHighestAttempt, boolean electiveAllowOneLowerCourse,
            String[] electivesAcceptedLowerCourses, String overallGPARequirement, String[] coreClassListRequirement,
            String optionalCoreAmountRequired, String[] optionsCoreClassListRequirement, String[] electiveClassListRequirement, boolean activeStatus) 
    {
        jsonDegree = new JSONDegree( degreeName,  coreRequirementAmount,  coreGPARequirement, 
             coreReplaceHighestAttempt,  coreAllowSeventhElective,  electiveRequirementAmount,
             electiveGPARequirement,  electiveReplaceHighestAttempt,  electiveAllowOneLowerCourse,
             electivesAcceptedLowerCourses,  overallGPARequirement,  coreClassListRequirement, optionalCoreAmountRequired,
             optionsCoreClassListRequirement, electiveClassListRequirement,  activeStatus);
    }

    public JSONDegreeWrapper(JSONDegree copyDegree) 
    {
        jsonDegree = new JSONDegree(copyDegree);
    }

    public JSONDegree getJsonDegree() {
        return jsonDegree;
    }

    public void setJsonDegree(JSONDegree jsonDegree) {
        this.jsonDegree = jsonDegree;
    }
}
