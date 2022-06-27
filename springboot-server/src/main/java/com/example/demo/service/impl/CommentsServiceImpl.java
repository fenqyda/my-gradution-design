package com.example.demo.service.impl;

import com.example.demo.entity.Comments;
import com.example.demo.mapper.CommentsMapper;
import com.example.demo.service.ICommentsService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author llm
 * @since 2022-04-15
 */
@Service
public class CommentsServiceImpl extends ServiceImpl<CommentsMapper, Comments> implements ICommentsService {
}
