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
public interface Robot {

    void moveTo(int x, int y);

    void workOn(String p, Tool t);
}
