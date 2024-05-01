package jobs4u.base.pluginGeneration.RequirementGenerator.application;


import jobs4u.base.pluginGeneration.RequirementGenerator.service.RequirementGenerator;

import java.util.Map;

public class GenerateRequirementTemplateController {


    private final RequirementGenerator requirementGenerator = new RequirementGenerator();


    public GenerateRequirementTemplateController() {
    }

    public void generateRequirementTemplate(Map<String, Map<String, String>> map, String fileName) {
        requirementGenerator.generateRequirement(map, fileName);
    }









}
