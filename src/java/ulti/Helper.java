/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ulti;

import java.util.Collections;
import java.util.List;

/**
 *
 * @author admin
 */
public class Helper {
    public static List pagination(List list,int pagenum,int pagesize){
        if(list == null || list.isEmpty()){
            return Collections.EMPTY_LIST;
        }
        return list.subList((pagenum - 1) * pagesize, Math.min(pagenum * pagesize, list.size()));
    }
}
