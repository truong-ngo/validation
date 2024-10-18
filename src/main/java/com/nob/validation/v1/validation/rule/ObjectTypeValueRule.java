package com.nob.validation.v1.validation.rule;

import com.nob.validation.v1.validation.policy.ObjectPolicy;

/**
 * Represent rule applied for object-type property
 * */
public interface ObjectTypeValueRule extends Rule {

    /**
     * Rule for inner object of property
     * */
    ObjectPolicy getInnerRule();
}
