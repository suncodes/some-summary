package suncodes.proxyFactory.target1;

/**
 * 目标实现类
 */
public class AOPTargetImpl implements AOPTargetInterface{
    @Override
    public void eat() {
        System.out.println("这个目标类的eat方法");
    }

    public void drink() {
        System.out.println("这是目标类的drink方法");
    }
}
