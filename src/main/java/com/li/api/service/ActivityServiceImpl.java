package com.li.api.service;

import com.li.api.exception.NotFoundException;
import com.li.api.pojo.model.Activity;
import com.li.api.repository.ActivityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * @author 黎源
 * @date 2020/12/30 18:55
 */
@Service
public class ActivityServiceImpl implements ActivityService {

    @Autowired
    private ActivityRepository repository;

    public Activity getActivity(String name) {
        Optional<Activity> activityOptional = repository.findByName(name);
        return activityOptional.orElseThrow(()->new NotFoundException(30005));
    }
}
