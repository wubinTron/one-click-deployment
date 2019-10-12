package tron.deployment.Controller;
import needPlanManager.NeedPlan;
import result.Result;
import result.ResultFactory;
import tron.deployment.service.serviceImpl.NeedPlanServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
public class needPlanController {

    @Autowired
    private NeedPlanServiceImpl needPlanService;

    @CrossOrigin
    @GetMapping("/needplans")
    @ResponseBody
    public List<NeedPlan> getNeedPlan(){
        return needPlanService.findAllNeedPlan();
    }


    @CrossOrigin
    @PostMapping("/needplans")
    @ResponseBody
    public Result addNeedPlan(@RequestBody NeedPlan needPlan){
        needPlan.getnet_type();
        needPlan.getport();
        needPlanService.addNeedplan(needPlan);
        return ResultFactory.bulidSuccessResult("添加成功");
    }

    @CrossOrigin
    @PutMapping("/needplanState")
    @ResponseBody
    public Result submitNeedplan(@RequestBody NeedPlan needPlan){

        System.out.println(needPlan.toString());
        
        needPlanService.submitNeedplan(needPlan);

        return ResultFactory.bulidSuccessResult("提交成功");
    }

    @CrossOrigin
    @PutMapping("/needplans")
    @ResponseBody
    public Result updateNeedplan(@RequestBody NeedPlan needPlan){

        System.out.println(needPlan.toString());
        needPlan.setport(needPlan.getport());
        needPlanService.updateNeedplan(needPlan);

        return ResultFactory.bulidSuccessResult("提交成功");
    }

}
