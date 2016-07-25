/**
 *
 */
package com.jy.recycle.util.mutiphotochoser.utils;


import com.jy.recycle.util.mutiphotochoser.model.ImageBean;

/**
 * @author xiaolf1
 */
public interface ChoseImageListener {

    public boolean onSelected(ImageBean image);

    public boolean onCancelSelect(ImageBean image);
}
