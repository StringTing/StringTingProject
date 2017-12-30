package com.string.leeyun.stringting_android.API;

import java.util.List;

/**
 * Created by leeyun on 2017. 12. 30..
 */

public class images {

       private List<approved>approved;


        public class approved{
            int id;
            String name;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

        }


    public List<images.approved> getApproved() {
        return approved;
    }

    public void setApproved(List<images.approved> approved) {
        this.approved = approved;
    }
}
