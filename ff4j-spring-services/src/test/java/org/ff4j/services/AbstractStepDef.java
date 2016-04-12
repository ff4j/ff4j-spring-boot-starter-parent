/*
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 *
 * Copyright 2013-2016 the original author or authors.
 */

package org.ff4j.services;

import org.ff4j.FF4j;
import org.ff4j.core.Feature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

import java.util.Arrays;
import java.util.List;

/**
 * Created by Paul
 *
 * @author <a href="mailto:paul58914080@gmail.com">Paul Williams</a>
 */
@ContextConfiguration(classes = {CucumberConfiguration.class})
public class AbstractStepDef {

    @Autowired
    protected FF4j ff4j;

    protected void createFeatures(List<FeaturePojo> features) {
        for (FeaturePojo featurePojo : features) {
            Feature feature = new Feature(featurePojo.getUid(), Boolean.valueOf(featurePojo.getEnable()),
                    featurePojo.getDescription(), featurePojo.getGroup(),
                    Arrays.asList(featurePojo.getPermissions().split(",")));
            createFeature(feature);
        }
    }
    protected void createFeature(Feature feature) {
        ff4j.createFeature(feature);
    }


    protected class FeaturePojo {
        private String uid;
        private String enable;
        private String description;
        private String group;
        private String permissions;

        public String getUid() {
            return uid;
        }

        public void setUid(String uid) {
            this.uid = uid;
        }

        public String getEnable() {
            return enable;
        }

        public void setEnable(String enable) {
            this.enable = enable;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getGroup() {
            return group;
        }

        public void setGroup(String group) {
            this.group = group;
        }

        public String getPermissions() {
            return permissions;
        }

        public void setPermissions(String permissions) {
            this.permissions = permissions;
        }
    }
}
