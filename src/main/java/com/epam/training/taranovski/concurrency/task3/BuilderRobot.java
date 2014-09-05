/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.epam.training.taranovski.concurrency.task3;

/**
 *
 * @author Alyx
 */
public class BuilderRobot extends MyRobot {

    public void workOn(String p, Tool t) {
        if (t.isDestructive()) {
            t = Tool.RATCHET;
        }
        super.workOn(p, t);
    }
}
