package tron.deployment.service;

/**
 * @author tron
 */
import needPlanManager.NeedPlan;

import java.util.List;

public interface NeedPlanService {

    List<NeedPlan> findAllNeedPlan();

    void addNeedplan(NeedPlan needPlan);

    public void submitNeedplan(NeedPlan needPlan);

    public void updateNeedplan(NeedPlan needPlan);

    public List<NeedPlan> findNeedplanBystate(NeedPlan needPlan);


}
