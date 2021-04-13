package org.jeecg.modules.activiti.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.aspect.annotation.AutoLog;

import org.jeecg.common.system.vo.LoginUser;
import org.jeecg.modules.activiti.entity.ActivitiEntity;
import org.jeecg.modules.activiti.entity.ActivitiHisEntity;
import org.jeecg.modules.activiti.service.IActivitiService;
import org.jeecg.modules.activiti.util.Constant;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.activiti.engine.HistoryService;
import org.activiti.engine.ProcessEngineConfiguration;
import org.apache.shiro.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author Hujw
 * @Description 工作流完整控制类，基于源znrl项目改造，目的是为了将所有的互交改为JsonAPI模式，不再进行视图控制跳转，该功能由前端实现
 * 整个模块作为类似切面模块嵌入系统，方便各种移植
 * 本功能模块如需进行分布式改造时可直接作为一个独立的服务，其也支持独立改造，更加方便后期开发改造
 * 本模块所有请求API皆以/activiti开头以方便区分，例如系统管理为/sys开头
 * @Date 14:27 2021/1/29
 * @Param
 * @return
 **/
@RestController
@RequestMapping("/activiti")
@Api(tags="工作流引擎")
@Slf4j
public class ActivitiController {
    protected Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private IActivitiService activitiService;

    @Autowired
    protected ProcessEngineConfiguration processEngineConfiguration;

    @Autowired
    HistoryService historyService;

    /**
     * todo 改造要一步步的来
     * 1、界面开发工作流，并配置执行（审批）人，将执行（审批）人信息存入数据库指定表，方便后续读取
     * 2、将工作流文件以bpmn格式存入对应的位置
     * 3、查询可发布资源、以json格式返回前台以便展示
     * 4、发布流程，根据前台调用api的bpmn文件名称参数发布对应的bpmn文件，尽可能实现一键发布
     * 5、启动流程，根据ProcessKey（流程名称）启动对应流程
     * 6、查看代办任务，将用户id作为assignee查询对应用户的待办任务，assignee配置在第一步已经完成
     * 7、根据任务ID查看任务的节点的详情
     * 8、执行任务节点，由监听器根据类型分为两种，执行Java类和存储过程
     * 9、
     *
     */

    /**
     * 查询可发布资源
     *
     * @return
     */
    @AutoLog(value = "查询可发布资源")
    @ApiOperation(value = "查询可发布资源", notes = "查询可发布资源")
    @GetMapping(value = "/listToDeployBpmn")
    @ResponseBody
    public Result listToDeployBpmn() {
        List<ActivitiEntity> activitiList = new ArrayList<ActivitiEntity>();
        try {
            activitiList = activitiService.listToDeployBpmn();
        } catch (Exception e) {
            e.printStackTrace();
        }
        log.info("查询到" +activitiList.size()+ "条可发布流程！");
        return Result.OK(activitiList);
    }

    /**
     * 发布流程
     *
     * @return
     */
    @AutoLog(value = "发布流程")
    @ApiOperation(value = "发布流程", notes = "发布流程")
    @PostMapping(value = "/deployBpmn")
    @ResponseBody
    public Result deployBpmn(@RequestBody ActivitiEntity activitiEntity) {
        Result result = new Result();
        try {
            result = activitiService.deployFlow(activitiEntity.getProcessKey());
        } catch (Exception e) {
            e.printStackTrace();
            result.setMessage("发布流程失败：" + e.getMessage());
            result.setSuccess(false);
        }
        //log.info("发布" + activitiEntity.getProcessKey() + "流程成功！");
        return result;
    }

    /**
     * 启动流程， ProcessKey为流程名称，必填; 其它业务参数组成jsonString输入
     *
     * @return
     */
    @AutoLog(value = "启动流程")
    @ApiOperation(value = "启动流程", notes = "启动流程")
    @PostMapping(value = "/startProcess")
    @ResponseBody
    public Result startProcess(@RequestBody ActivitiEntity activitiEntity) {
        Result result = new Result();
        try {
            //获取发起流程操作用户信息
            LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
            activitiEntity.setOpCode(sysUser.getId());
            activitiEntity.setOpName(sysUser.getRealname());
            /**
             * 调试的时候直接给操作人赋值
             */
//            activitiEntity.setOpCode("e9ca23d68d884d4ebb19d07889727dae");
//            activitiEntity.setOpName("管理员");


            //把json字符串里追加发起审批人
            String jsonString = activitiEntity.getJsonString();
            JSONObject jo = JSON.parseObject(jsonString);

            //JSONObject jo = JSON.parseObject("{\"apprEventTypeCd\":\"UploadProcess_RC_CZH_1\"}");

            jo.put("startProcOpCode", activitiEntity.getOpCode());
            //todo 调试完成下方需要放开，上方需要注掉
            //jo.put("startProcOpCode", sysUser.getId());
            activitiEntity.setJsonString(jo.toJSONString());

            result = activitiService.startProcess(activitiEntity);
        } catch (Exception e) {
            e.printStackTrace();
            result.setMessage("启动" + activitiEntity.getProcessKey() + "流程失败：" + e.getMessage());
            result.setSuccess(false);
            log.info("启动" + activitiEntity.getProcessKey() + "流程失败！");
        }
        //log.info("启动" + activitiEntity.getProcessKey() + "流程成功！");
        return result;
    }


    /**
     * 查看待办任务 （包括待办的和待领取的）
     *
     * @return
     */
    @AutoLog(value = "查看待办任务")
    @ApiOperation(value = "查看待办任务", notes = "查看待办任务")
    @GetMapping(value = "/qryTodoTasks")
    @ResponseBody
    public Result qryTodoTasks(ActivitiEntity activitiEntity) {
        Result result = new Result();
        //获取登录操作员的ID
        LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
        if(sysUser != null){
            String assignee = sysUser.getId();
            String processKey = activitiEntity.getProcessKey();
            try {
                result = activitiService.qryTodoTasks(assignee, processKey);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }else{
            //todo 调试使用，调试过后一定要去掉
            String assignee = "e9ca23d68d884d4ebb19d07889727dae";
            String processKey = activitiEntity.getProcessKey();
            try {
                result = activitiService.qryTodoTasks(assignee, processKey);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        //log.info("用户：" + sysUser.getRealname() + "，查看了待办任务！");
        return result;
    }

    /**
     * 根据任务ID查看任务的节点的详情
     *
     * @return
     */
    @AutoLog(value = "根据任务ID查看任务的节点的详情")
    @ApiOperation(value = "根据任务ID查看任务的节点的详情", notes = "根据任务ID查看任务的节点的详情")
    @PostMapping(value = "/qryTaskById")
    @ResponseBody
    public Result qryTaskById(@RequestBody ActivitiEntity activitiEntity) {
        Result result = new Result();
        String taskId = activitiEntity.getTaskId();
        try {
            result = activitiService.qryTaskById(taskId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        log.info("查看任务节点的详情！");
        return result;
    }

    /**
     * 执行任务节点
     *
     * @return
     */
    @AutoLog(value = "执行任务节点")
    @ApiOperation(value = "执行任务节点", notes = "执行任务节点")
    @PostMapping(value = "/doTask")
    @ResponseBody
    public Result doTask(@RequestBody ActivitiEntity activitiEntity) {
        Result result = new Result();
        try {
            result = activitiService.doTask(activitiEntity);
        } catch (Exception e) {
            e.printStackTrace();
            result.setMessage("任务节点执行失败：" + e.getMessage());
            result.setSuccess(false);
            log.info("任务节点" + activitiEntity.taskDo + "执行失败！");
        }
        //log.info("任务节点" + activitiEntity.taskDo + "执行成功！");
        return result;
    }


    /**
     * 查看任务的历史信息
     *
     * @return
     */
    @AutoLog(value = "查看任务的历史信息")
    @ApiOperation(value = "查看任务的历史信息", notes = "查看任务的历史信息")
    @PostMapping(value = "/qryHistoryProcessInst")
    @ResponseBody
    public Result qryHistoryProcessInst(@RequestBody ActivitiHisEntity activitiHisEntity) {
        Result result = new Result();
        try {
            result = activitiService.qryHistoryProcessInst(activitiHisEntity.getProcessInstanceId());
        } catch (Exception e) {
            e.printStackTrace();
        }
        log.info("成功查看任务的历史信息！");
        return result;
    }

    /**
     * 自定义读取带跟踪流程路径图片
     *
     * @throws Exception
     */
    @AutoLog(value = "自定义读取带跟踪流程路径图片")
    @ApiOperation(value = "自定义读取带跟踪流程路径图片", notes = "自定义读取带跟踪流程路径图片")
    @GetMapping(value = "/traceImage/{processInstId}")
    public void traceImage(@PathVariable("processInstId") String processInstId, HttpServletResponse response) throws Exception {
        try {
            InputStream is = activitiService.generateImgInputStream(processInstId);

            int len = 0;
            byte[] b = new byte[1024];

            while ((len = is.read(b, 0, 1024)) != -1) {
                response.getOutputStream().write(b, 0, len);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 查询审批历史记录
     *
     * @throws Exception
     */
    @AutoLog(value = "查询审批历史记录")
    @ApiOperation(value = "查询审批历史记录", notes = "查询审批历史记录")
    @GetMapping(value = "/qryTasksHisList")
    @ResponseBody
    public Result qryTasksHisList(ActivitiHisEntity activitiHisEntity) {
        //需要分页的实体bean都用父类定义，方便设置page相关参数
        Result result = new Result();
        try {
            result = activitiService.qryTasksHisList(activitiHisEntity);
        } catch (Exception e) {
            e.printStackTrace();
        }
        log.info("查询审批历史记录！");
        return result;
    }

    /**
     * 查询审批历史记录子表
     *
     * @throws Exception
     */
    @AutoLog(value = "查询审批历史记录子表")
    @ApiOperation(value = "查询审批历史记录子表", notes = "查询审批历史记录子表")
    @PostMapping(value = "/qryTasksHisChildList")
    @ResponseBody
    public Result qryTasksHisChildList(ActivitiHisEntity activitiHisEntity) {
        Result result = new Result();
        //todo 这里后续需要加个查询分页，否则随着时间积累数据太多影响响应
        try {
            result = activitiService.qryTasksHisChildList(activitiHisEntity);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 推动ReceiveTask流程向下流转
     *
     * @return
     */
    @AutoLog(value = "推动ReceiveTask流程向下流转")
    @ApiOperation(value = "推动ReceiveTask流程向下流转", notes = "推动ReceiveTask流程向下流转")
    @PostMapping(value = "/signal")
    @ResponseBody
    public Result signal(@RequestParam("processInstId") String processInstId) {
        Result result = new Result();
        try {
            result = activitiService.signal(processInstId);
        } catch (Exception e) {
            e.printStackTrace();
            result.setMessage("推动任务流程向下流转失败：" + e.getMessage());
            result.setSuccess(false);
        }
        log.info("流程" + processInstId + "成功进入下一步！");
        return result;
    }

    /**
     * 暂停流程实例
     *
     * @param processInstId
     * @return
     */
    @AutoLog(value = "暂停流程实例")
    @ApiOperation(value = "暂停流程实例", notes = "暂停流程实例")
    @PostMapping(value = "/pause")
    @ResponseBody
    public Result pause(@RequestParam("processInstId") String processInstId) {
        // 此处仅设置流程状态flag为P-暂停；流程实例运行时检查到该状态，执行对应的暂停动作
        Constant.setCache(processInstId + "status", "P");
        log.info("流程" + processInstId + "已被暂停！");
        return new Result();
    }



    /**
     * 激活被挂起的流程实例。（包括：遇到异常被挂起、用户操作“暂停”按钮挂起
     *
     * @param processInstId
     * @return
     */
    @AutoLog(value = "激活被挂起的流程实例")
    @ApiOperation(value = "激活被挂起的流程实例", notes = "激活被挂起的流程实例")
    @PostMapping(value = "/activateProcess")
    @ResponseBody
    public Result activateProcess(@RequestParam("processInstId") String processInstId) {
        Result result = new Result();
        String procStatus = null == Constant.getCache(processInstId + "status") ? null : Constant.getCache(processInstId + "status").toString();
        try {
            Constant.setCache(processInstId + "status", "R"); // 流程状态恢复为“运行中”
            result = activitiService.activateProcess(processInstId);
        } catch (Exception e) {
            Constant.setCache(processInstId + "status", procStatus);
            e.printStackTrace();
            result.setMessage("激活流程失败：" + e.getMessage());
            result.setSuccess(false);
        }
        log.info("流程" + processInstId + "已被重新激活！");
        return result;
    }

    /**
     * 设置缓存值
     *
     * @param key
     * @param value
     */
    @AutoLog(value = "设置缓存值")
    @ApiOperation(value = "设置缓存值", notes = "设置缓存值")
    @PostMapping(value = "/setCache")
    @ResponseBody
    public void setCache(@RequestParam("key") String key, @RequestParam("value") String value) {
        Constant.setCache(key, value);
        log.info("缓存值：" + value + "设置成功！");
    }

    /**
     * 查询流程实例运行状态
     * @param processInstId
     * @return R-运行中；P-暂停；F-故障；E-结束
     */
    @AutoLog(value = "查询流程实例运行状态")
    @ApiOperation(value = "查询流程实例运行状态", notes = "查询流程实例运行状态")
    @PostMapping(value = "/qryProcStatus")
    @ResponseBody
    public Result qryProcStatus(String processInstId){
        return Result.OK(activitiService.qryProcStatus(processInstId));
    }

    /**
     * 获取流程定义上各个节点的具体的信息
     * @param processInstId
     * @return
     * @throws Exception
     */
    @AutoLog(value = "获取流程定义上各个节点的具体的信息")
    @ApiOperation(value = "获取流程定义上各个节点的具体的信息", notes = "获取流程定义上各个节点的具体的信息")
    @PostMapping("/getProcessTrace")
    @ResponseBody
    public Result getProcessTrace(String processInstId) throws Exception {
        return Result.OK(activitiService.getProcessTrace(processInstId));
    }

    //todo 工作流视图控制全部交由前端控制，此处不再进行管理，但前端开发可以参考下方源视图文件进行改造，方便开发
    /*//模拟工作流开发测试用（临时）
    @RequestMapping(value = "/workFlowTest", method = RequestMethod.GET)
    public String gotoWorkFlowTest() {
        return "/activiti/workFlowTest";
    }

    //发布工作流
    @RequestMapping(value = "/deployWorkFlow", method = RequestMethod.GET)
    public String gotoDeployWorkFlow() {
        return "/activiti/deployWorkFlow";
    }

    //审批历史查询页面
    @RequestMapping(value = "/qryTaskHisList", method = RequestMethod.GET)
    public String gotoTasksHisPage() {
        return "/activiti/qryTaskHisList";
    }*/

//    //我的待办任务
//    @ApiOperation("我的待办任务")
//    @RequestMapping(value = "/myTodoList", method = RequestMethod.GET)
//    public ModelAndView gotoMyTodoList() {
//        ModelAndView modelAndView = new ModelAndView();
//        String taskId = request.getParameter("taskId") == null ? "" : request.getParameter("taskId");
//        modelAndView.addObject("taskId", taskId);
//
//        modelAndView.setViewName("/activiti/myTodoList");
//        return modelAndView;
//    }

    //流程详情
/*    @RequestMapping(value = "/workFlowDisplay", method = RequestMethod.GET)
    public String gotoWorkFlowDisplay(@RequestParam Map<String, Object> params, ModelMap modelMap) {
        modelMap.put("processInstIdHidden", MapUtils.getString(params,"processInstIdHidden"));
        return "/activiti/workFlowDisplay";
    }*/

    /**
     * 查询可发布资源
     *
     * @return
     */
//    @RequestMapping(value = "/listToDeployBpmn")
//    @ResponseBody
//    public GridModel listToDeployBpmn() {
//        GridModel g = null;
//        try {
//            g = activitiService.listToDeployBpmn();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return g;
//    }

/*    *//**
     * 发布流程
     *
     * @return
     *//*
    @RequestMapping(value = "/deployBpmn")
    @ResponseBody
    public ResultData deployBpmn() {
        ActivitiEntity activitiEntity = searchForm(ActivitiEntity.class);
        ResultData r = null;
        try {
            r = activitiService.deployFlow(activitiEntity.getProcessKey());
        } catch (Exception e) {
            e.printStackTrace();
            r = new ResultData();
            r.setResultMsg("启动任务失败：" + e.getMessage());
            r.setSuccess(false);
        }
        return r;
    }*/

   /* *//**
     * 启动流程， ProcessKey为流程名称，必填; 其它业务参数组成jsonString输入
     *
     * @return
     *//*
    @RequestMapping(value = "/startProcess")
    @ResponseBody
    public ResultData startProcess() {
        ActivitiEntity activitiEntity = searchForm(ActivitiEntity.class);
        ResultData r;
        try {
            LoginInfo currentUser = super.getCurrentUser();
            activitiEntity.setOpCode(super.getUserId());
            activitiEntity.setOpName(currentUser.getRealName());


            //把json字符串里追加发起审批人
            String jsonString = activitiEntity.getJsonString();
            JSONObject jo = JSON.parseObject(jsonString);
            jo.put("startProcOpCode", super.getUserId());
            activitiEntity.setJsonString(jo.toJSONString());

            r = activitiService.startProcess(activitiEntity);
        } catch (Exception e) {
            e.printStackTrace();
            r = new ResultData();
            r.setResultMsg("开始启动任务失败：" + e.getMessage());
            r.setSuccess(false);
        }
        return r;
    }*/

    //todo 这个功能暂时先放放，后续完善
    /**
     * 启动管控模拟流程， ProcessKey为流程名称，必填; 其它业务参数组成jsonString输入
     *
     * @return
     * @Param
     *//*
    @RequestMapping(value = "/startMockProcess", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public ResultData startMockProcess() {
        ActivitiEntity activitiEntity = searchForm(ActivitiEntity.class);
        Map<String, Object> processParam = CommonUtil.getRequestParamMap(request);
        // 查询流程配置的步骤+步骤执行方法配置。
        List<ActStepConfigEntity> actStepConfigEntityList = activitiService.qryActStepMethodList(processParam.get("processKey").toString());
        processParam.put("actStepConfigEntityList", actStepConfigEntityList);
        processParam.put("stepInfoMap", new HashMap<>()); //流程中每个环节的步骤执行信息，Map中的key=环节id；value为步骤执行信息列表。
        ResultData r;
        try {
            LoginInfo currentUser = null == request.getSession().getAttribute(SecurityConstants.LOGIN_USER) ? null : (LoginInfo) request.getSession().getAttribute(SecurityConstants.LOGIN_USER);
            activitiEntity.setOpCode(currentUser.getUserId());
            activitiEntity.setOpName(currentUser.getRealName());
            activitiEntity.setProcessKey(processParam.get("processKey").toString());
            processParam.put("traineeGroupId", currentUser.getTraineeGroupId());
            processParam.put("userId", currentUser.getUserId());

            //把json字符串里追加发起审批人
            String jsonString = activitiEntity.getJsonString();
            JSONObject jo = JSON.parseObject(jsonString);
            jo.put("startProcOpCode", currentUser.getUserId());
            activitiEntity.setJsonString(jo.toJSONString());
            processParam.put("minWeight", processParam.get("minWeight").toString());
            processParam.put("maxWeight", processParam.get("maxWeight").toString());
            r = activitiService.startProcess(activitiEntity);//做化验室时，改动过
        } catch (Exception e) {
            e.printStackTrace();
            r = new ResultData();
            r.setResultMsg("开始启动任务失败：" + e.getMessage());
            r.setSuccess(false);
        }
        return r;
    }*/

    /**
     * 查看待办任务 （包括待办的和待领取的）
     *
     * @return
     *//*
    @RequestMapping(value = "/qryTodoTasks")
    @ResponseBody
    public GridModel qryTodoTasks() {
        GridModel g = null;
        //获取登录操作员的ID
        Object ob = request.getSession().getAttribute(SecurityConstants.LOGIN_USER);
        if(ob != null){
            LoginInfo currentUser = (LoginInfo) ob;
            String assignee = currentUser.getUserId();
            ActivitiEntity activitiEntity = searchForm(ActivitiEntity.class);
            String processKey = activitiEntity.getProcessKey();
            try {
                g = activitiService.qryTodoTasks(assignee, processKey);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return g;
    }*/


    /**
     * 根据任务ID查看任务的节点的详情
     *
     * @return
     *//*
    @RequestMapping(value = "/qryTaskById")
    @ResponseBody
    public GridModel qryTaskById() {
        GridModel g = null;

        ActivitiEntity activitiEntity = searchForm(ActivitiEntity.class);
        String taskId = activitiEntity.getTaskId();

        try {
            g = activitiService.qryTaskById(taskId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return g;
    }*/


    /**
     * 执行任务节点
     *
     * @return
     *//*
    @RequestMapping(value = "/doTask")
    @ResponseBody
    public ResultData doTask() {
        ActivitiEntity activitiEntity = SearchForm(ActivitiEntity.class);
        ResultData r;
        try {
            r = activitiService.doTask(activitiEntity);
        } catch (Exception e) {
            e.printStackTrace();
            r = new ResultData();
            r.setResultMsg("任务节点执行失败：" + e.getMessage());
            r.setSuccess(false);
        }
        return r;
    }
*/

/*    *//**
     * 查看任务的历史信息
     *
     * @return
     *//*
    @RequestMapping(value = "/qryHistoryProcessInst")
    @ResponseBody
    public GridModel qryHistoryProcessInst() {
        ActivitiHisEntity activitiHisEntity = SearchForm(ActivitiHisEntity.class);
        GridModel g = null;

        try {
            g = activitiService.qryHistoryProcessInst(activitiHisEntity.getProcessInstanceId());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return g;
    }*/



    /**
     * 查询审批历史记录
     *
     * @throws Exception
     *//*
    @RequestMapping(value = "/qryTasksHisList")
    @ResponseBody
    public GridModel qryTasksHisList() {
        //需要分页的实体bean都用父类定义，方便设置page相关参数 xieyt
        ActivitiHisEntity activitiHisRpt = searchForm(ActivitiHisEntity.class);
        activitiHisRpt.setPageRowIndex(getPageIndex(), getPageSize());
        //查询分页
        GridModel gridModel = null;
        try {
            gridModel = activitiService.qryTasksHisList(activitiHisRpt);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return gridModel;
    }*/

    /**
     * 查询审批历史记录子表
     *
     * @throws Exception
     *//*
    @RequestMapping(value = "/qryTasksHisChildList")
    @ResponseBody
    public GridModel qryTasksHisChildList() {
        //需要分页的实体bean都用父类定义，方便设置page相关参数 xieyt
        ActivitiHisEntity activitiHisChildRpt = searchForm(ActivitiHisEntity.class);

        //查询分页
        GridModel gridModel = null;
        try {
            gridModel = activitiService.qryTasksHisChildList(activitiHisChildRpt);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return gridModel;
    }
*/
    /**
     * 推动ReceiveTask流程向下流转
     *
     * @return
     *//*
    @RequestMapping(value = "/signal")
    @ResponseBody
    public ResultData signal(@RequestParam("processInstId") String processInstId) {
        ResultData r;
        try {
            r = activitiService.signal(processInstId);
        } catch (Exception e) {
            e.printStackTrace();
            r = new ResultData();
            r.setResultMsg("推动任务流程向下流转失败：" + e.getMessage());
            r.setSuccess(false);
        }
        return r;
    }*/

    /**
     * 暂停流程实例
     *
     * @param processInstId
     * @return
     *//*
    @RequestMapping(value = "/pause")
    @ResponseBody
    public ResultData pause(@RequestParam("processInstId") String processInstId) {
        // 此处仅设置流程状态flag为P-暂停；流程实例运行时检查到该状态，执行对应的暂停动作
        Constant.setCache(processInstId + "status", "P");
        return new ResultData();
    }
*/

/*    *//**
     * 激活被挂起的流程实例。（包括：遇到异常被挂起、用户操作“暂停”按钮挂起
     *
     * @param processInstId
     * @return
     *//*
    @RequestMapping(value = "/activateProcess")
    @ResponseBody
    public ResultData activateProcess(@RequestParam("processInstId") String processInstId) {
        ResultData r;
        String procStatus = null == Constant.getCache(processInstId + "status") ? null : Constant.getCache(processInstId + "status").toString();
        try {
            Constant.setCache(processInstId + "status", "R"); // 流程状态恢复为“运行中”
            r = activitiService.activateProcess(processInstId);
        } catch (Exception e) {
            Constant.setCache(processInstId + "status", procStatus);
            e.printStackTrace();
            r = new ResultData();
            r.setResultMsg("激活流程失败：" + e.getMessage());
            r.setSuccess(false);
        }
        return r;
    }*/

}
