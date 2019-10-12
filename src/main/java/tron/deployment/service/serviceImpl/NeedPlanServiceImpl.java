package tron.deployment.service.serviceImpl;

import needPlanManager.NeedPlan;
import tron.deployment.service.NeedPlanService;
import mapper.NeedPlanMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
/**
 * 
 * @author tron
 * 
 *
 */

@Service
public class NeedPlanServiceImpl implements NeedPlanService {

	@Autowired
	private NeedPlanMapper needPlanMapper;
	
	@Override
	public List<NeedPlan> findAllNeedPlan() {
		return needPlanMapper.findAllNeedPlan();
	}

	@Override
	public void addNeedplan(NeedPlan needPlan) {
		needPlanMapper.addNeedplan(needPlan);
	}

	@Override
	public void updateNeedplan(NeedPlan needPlan) {
		needPlanMapper.updateNeedPlan(needPlan);
	}

	@Override
	public void submitNeedplan(NeedPlan needPlan) {
		needPlanMapper.submitNeedplan(needPlan);
	}

	@Override
	public List<NeedPlan> findNeedplanBystate(NeedPlan needPlan) {
		return needPlanMapper.findNeedplanBystate(needPlan);
	}

}
