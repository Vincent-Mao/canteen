package com.fast.canteen.controller;

import java.util.List;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.fast.system.general.core.controller.BaseController;
import com.fast.system.general.core.domain.AjaxResult;

import java.io.InputStream;

import org.springframework.web.multipart.MultipartFile;
import com.fast.canteen.domain.Feedback;
import com.fast.canteen.service.IFeedbackService;
import com.fast.system.general.utils.poi.ExcelUtil;
import com.fast.system.general.core.page.TableDataInfo;

/**
 * 意见反馈Controller
 *
 * @author fast
 * @date 2025-11-23
 */
@RestController
@RequestMapping("/canteen/feedback")
public class FeedbackController extends BaseController {
    @Autowired
    private IFeedbackService feedbackService;

    /**
     * 查询意见反馈列表
     */
    @GetMapping("/list")
    public TableDataInfo list(Feedback feedback) {
        startPage();
        List<Feedback> list = feedbackService.selectFeedbackList(feedback);
        return getDataTable(list);
    }

    /**
     * 导出意见反馈列表
     */
    @PostMapping("/export")
    public void export(HttpServletResponse response, Feedback feedback) {
        List<Feedback> list = feedbackService.selectFeedbackList(feedback);
        ExcelUtil<Feedback> util = new ExcelUtil<Feedback>(Feedback. class);
        util.exportExcel(response, list, "意见反馈数据");
    }

    /**
     * 下载模板
     */
    @PostMapping("/importTemplate")
    public void importTemplate(HttpServletResponse response) {
        ExcelUtil<Feedback> util = new ExcelUtil<Feedback>(Feedback. class);
        util.importTemplateExcel(response, "意见反馈数据");
    }

    /**
     * 导入数据
     */
    @PostMapping("/importData")
    public AjaxResult importData(MultipartFile file) throws Exception {
        ExcelUtil<Feedback> util = new ExcelUtil<Feedback>(Feedback. class);
        InputStream inputStream = file.getInputStream();
        List<Feedback> list = util.importExcel(inputStream);
        inputStream.close();
        int count = feedbackService.batchInsertFeedback(list);
        return AjaxResult.success("导入成功" + count + "条信息！");
    }

    /**
     * 获取意见反馈详细信息
     */
    @GetMapping(value = "/{feedbackId}")
    public AjaxResult getInfo(@PathVariable("feedbackId") String feedbackId) {
        return success(feedbackService.selectFeedbackByFeedbackId(feedbackId));
    }

    /**
     * 新增意见反馈
     */
    @PostMapping
    public AjaxResult add(@RequestBody Feedback feedback) {
        return toAjax(feedbackService.insertFeedback(feedback));
    }

    /**
     * 修改意见反馈
     */
    @PutMapping
    public AjaxResult edit(@RequestBody Feedback feedback) {
        return toAjax(feedbackService.updateFeedback(feedback));
    }

    /**
     * 删除意见反馈
     */
    @DeleteMapping("/{feedbackIds}")
    public AjaxResult remove(@PathVariable String[] feedbackIds) {
        return toAjax(feedbackService.deleteFeedbackByFeedbackIds(feedbackIds));
    }
}
