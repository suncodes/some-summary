package suncodes.beans;

public interface BeanFactoryAware extends Aware {

	void setBeanFactory(BeanFactory bf);
}
