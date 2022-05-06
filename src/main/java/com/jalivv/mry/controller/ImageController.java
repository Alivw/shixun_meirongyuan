package com.jalivv.mry.controller;

import com.jalivv.mry.entity.Image;
import com.jalivv.mry.entity.R;
import com.jalivv.mry.service.ImageService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

import static org.springframework.http.ResponseEntity.ok;

/**
 * (Image)表控制层
 *
 * @author makejava
 * @since 2022-05-05 16:59:15
 */
@RestController
@RequestMapping("image")
@Api(tags = "图片模块")
public class ImageController {


    private static final Logger logger = LoggerFactory.getLogger(ImageController.class);


    /**
     * 服务对象
     */
    @Resource
    private ImageService imageService;

    /**
     * 分页查询
     *
     * @param image       筛选条件
     * @param pageRequest 分页对象
     * @return 查询结果
     */
    @GetMapping
    public ResponseEntity<Page<Image>> queryByPage(Image image, PageRequest pageRequest) {
        return ok(this.imageService.queryByPage(image, pageRequest));
    }

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("{id}")
    public ResponseEntity<Image> queryById(@PathVariable("id") Long id) {
        return ok(this.imageService.queryById(id));
    }

    /**
     * 新增数据
     *
     * @param image 实体
     * @return 新增结果
     */
    @PostMapping
    public ResponseEntity<Image> add(Image image) {
        return ok(this.imageService.insert(image));
    }

    /**
     * 编辑数据
     *
     * @param image 实体
     * @return 编辑结果
     */
    @PutMapping
    public ResponseEntity<Image> edit(Image image) {
        return ok(this.imageService.update(image));
    }

    /**
     * 删除数据
     *
     * @param id 主键
     * @return 删除是否成功
     */
    @DeleteMapping
    public ResponseEntity<Boolean> deleteById(Long id) {
        return ok(this.imageService.deleteById(id));
    }


    @GetMapping("/type/{banner}")
    @ApiOperation("查询所有轮播图图片")
    public R banner(@PathVariable("banner") String type) {
        try {
            Assert.notNull(type, "请选择类型");
            List<Image> images = imageService.queryByType(type);
            return R.ok(images);
        } catch (Exception e) {
            logger.error("{}", e.getMessage());
            return R.error("查询数据失败!" + e.getMessage(), null);
        }
    }
}

