
package org.acme;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.kie.kogito.app.DecisionModels;
import org.kie.dmn.api.core.DMNMessage;
import org.kie.dmn.api.core.ast.DecisionNode;
import org.kie.kogito.app.Application;
import org.kie.dmn.validation.DMNValidator;
import org.kie.dmn.validation.DMNValidatorFactory;
import org.kie.dmn.model.api.Definitions;
import org.kie.dmn.model.api.ItemDefinition;
import java.util.List;


@org.springframework.stereotype.Component
@RestController
@ComponentScan("org.kie.kogito.app")
public class HomeController {

    @org.springframework.beans.factory.annotation.Autowired() @Qualifier("application")
    Application application;

    //@org.springframework.beans.factory.annotation.Autowired() @Qualifier("CapApp")
    //CapApplication capapp;



    @GetMapping("/declist")
    public Map<String, String> DecisionList() {
        HashMap<String, String> map = new HashMap<>();
        map.put("key", "value");
        map.put("foo", "bar");
        map.put("aa", "bb");

        //org.kie.kogito.decision.DecisionModel decision = application.get(org.kie.kogito.decision.DecisionModels.class).getDecisionModel("https://github.com/kiegroup/drools/kie-dmn/_A4BCA8B8-CF08-433F-93B2-A2598F19ECFF", "Traffic Violation");
        org.kie.kogito.decision.DecisionModel decision = application.get(org.kie.kogito.decision.DecisionModels.class).getDecisionModel("https://www.drools.org/kie-dmn/Dinner", "0020-dinner");
        
        //System.out.println(decision.toString());
        System.out.println(decision.getDMNModel().getName());

        Set<DecisionNode> nodes = decision.getDMNModel().getDecisions();

        map.put("dmn-name", decision.getDMNModel().getName());

        int i = 0;
        
        for (DecisionNode n : nodes)
        {
            map.put("var" + String.valueOf(i), n.getName());
            System.out.println(n.getName());
        }

        for (DMNMessage m : decision.getDMNModel().getMessages() )
        {
            System.out.println(m.getText());
        }

        Definitions definitions = decision.getDMNModel().getDefinitions();
        
        for (ItemDefinition item : definitions.getItemDefinition())
        {
            System.out.println(item.getName());
        }

        DMNValidator validator = DMNValidatorFactory.newValidator();
        
        List<DMNMessage> messages = validator.validate(definitions);

        System.out.println("validation stuff");

        for (DMNMessage m : messages)
        {
            System.out.println(m.getText());
        }



        //DecisionModels dc = applicationContext.getBean("DecisionModels",DecisionModels.class);
        //System.out.println(dc.toString());

        return map;
    }


}

