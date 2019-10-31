package mapper;

import needPlanManager.NeedPlan;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NeedPlanMapper {

    List<NeedPlan> findAllNeedPlan();

   
    List<NeedPlan> findNeedplanBystate(NeedPlan needPlan);

    void addNeedplan(NeedPlan needPlan);

   void submitNeedplan(NeedPlan needPlan);

    void updateNeedPlan(NeedPlan needPlan);

}
