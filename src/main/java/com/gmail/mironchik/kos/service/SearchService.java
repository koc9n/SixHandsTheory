package com.gmail.mironchik.kos.service;

import com.gmail.mironchik.kos.dto.Person;
import com.gmail.mironchik.kos.dto.SearchResult;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Created by kmironchyk on 8/31/2015.
 */
@Service
public class SearchService {

    @Autowired
    private VKService vkService;

    public void performSearch(SearchResult result) {
        Set<Long> leftFriends = new HashSet<>();
        Set<Long> rightFriends = new HashSet<>();

        leftFriends.add(result.getFirstPerson().getId());
        rightFriends.add()

        for (Long id : (
                result.getFirstPerson().getFriends().size() < result.getSecondPerson().getFriends().size())
                ? result.getFirstPerson().getFriends()
                : result.getSecondPerson().getFriends()) {

        }

        result.getHandShakes().add(null);
    }

    public Collection getMutualFriends(Set<Long> f_ids, Set<Long> s_ids) {
        if (f_ids == null || s_ids == null || f_ids.isEmpty() || s_ids.isEmpty()) {
            return null;
        }
        Collection result = CollectionUtils.intersection(f_ids, s_ids);
        if (result == null || result.isEmpty()) {
            return null;
        } else {
            return result;
        }
    }
}
