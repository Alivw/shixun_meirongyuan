package com.jalivv.mry.controller;

import com.jalivv.mry.entity.Page;
import com.jalivv.mry.entity.Project;
import com.jalivv.mry.entity.R;
import com.jalivv.mry.service.ProjectService;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * (Project)表控制层
 *
 * @author makejava
 * @since 2022-05-05 17:00:13
 */
@RestController
@RequestMapping("project")
public class ProjectController {


    private static final Logger logger = LoggerFactory.getLogger(ProjectController.class);


    /**
     * 服务对象
     */
    @Resource
    private ProjectService projectService;

    /**
     * 分页查询
     *
     * @param project 筛选条件
     * @param pageRequest      分页对象
     * @return 查询结果
     */
    // @GetMapping
    // public ResponseEntity<Page<Project>> queryByPage(Project project, PageRequest pageRequest) {
    //     return ResponseEntity.ok(this.projectService.queryByPage(project, pageRequest));
    // }

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("{id}")
    public ResponseEntity<Project> queryById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(this.projectService.queryById(id));
    }

    /**
     * 新增数据
     *
     * @param project 实体
     * @return 新增结果
     */
    @PostMapping
    public ResponseEntity<Project> add(Project project) {
        return ResponseEntity.ok(this.projectService.insert(project));
    }

    /**
     * 编辑数据
     *
     * @param project 实体
     * @return 编辑结果
     */
    @PutMapping
    public ResponseEntity<Project> edit(Project project) {
        return ResponseEntity.ok(this.projectService.update(project));
    }

    /**
     * 删除数据
     *
     * @param id 主键
     * @return 删除是否成功
     */
    @DeleteMapping
    public ResponseEntity<Boolean> deleteById(Long id) {
        return ResponseEntity.ok(this.projectService.deleteById(id));
    }


    @ApiOperation(value = "获取项目信息",notes = "获取所有的项目信息")
    @GetMapping("/")
    public R getProinfos(){
        try {
            List<Project> ps = projectService.getProinfos();

            return R.ok(ps);
        } catch (Exception e) {
            logger.error("{}", e.getMessage());
            return R.error("获取项目信息失败" + e.getMessage(), null);
        }
    }

    @ApiOperation(value = "获取项目信息",notes = "获取所有的项目信息")
    @GetMapping("/fatch")
    public R getProInfosFatch(Page page){
        try {

            List<Project> ps = projectService.getProinfos((page.getPageNo() - 1) * page.getPageSize(), page.getPageSize());

            return R.ok(ps);
        } catch (Exception e) {
            logger.error("{}", e.getMessage());
            return R.error("获取项目信息失败" + e.getMessage(), null);
        }
    }

}

