package com.test.unit9;

import java.security.Permission;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
/**
 * A Permission that checks for bad words.
 * @author REN
 *
 */
public class WordCheckPermission extends Permission {
    
    private String actions;
    public WordCheckPermission(String name, String actions) {
        super(name);
        this.actions = actions;
    }
    // 返回false,表示权限不够
    // other是当前生成的
    @Override
    public boolean implies(Permission other) {
        
        if (!(other instanceof WordCheckPermission)) return false;
        // 传递过来的name会有一个\r\n  坑坑坑啊
        WordCheckPermission b = (WordCheckPermission) other;
        /**
         * 总共由 insert和avoid操作
         * 名字是操作的对象, 如果是avoid操作,name给出的是"sex,drugs,c++"等禁止插入的元素.
         * 如果是insert操作, 用于插入具体文本的权限.
         */
        if ("insert".equals(actions)) {
            
            boolean t = b.actions.equals("insert") && getName().indexOf(other.getName()) >= 0;
            return t;
        }else if(actions.equals("avoid")) {
            if (b.actions.equals("avoid")) return b.badWordSet().containsAll(badWordSet());
            else if (b.actions.equals("insert")) {
                for (String badWord : badWordSet()) {
                    if (b.getName().indexOf(badWord) >= 0)
                        return false;
                }
                return true;
            }else {
                return false;
            }
        }else
            return false;
    }
    
    public Set<String> badWordSet()
    {
        Set<String> set = new HashSet<>();
        set.addAll(Arrays.asList(getName().split(",")));
        return set;
    }
    

    @Override
    public int hashCode() {
        return Objects.hash(getName(), actions);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        WordCheckPermission b = (WordCheckPermission) obj;
        if (!Objects.equals(actions, b.actions)) return false;
        if ("insert".equals(actions)) return Objects.equals(getName(), b.getName());
        else if("avoid".equals(actions)) return badWordSet().equals(b.badWordSet());
        else return false;
    }

    @Override
    public String getActions() {
        return actions;
    }

}
