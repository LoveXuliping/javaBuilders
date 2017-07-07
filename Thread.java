private void init(ThreadGroup g,Runnable target,String name,long stackSize){
		
		if (name==null) {
			throw new NullPointerException("name cannot be null");//抛出空指针异常
		}
		//当前线程就是该线程的父线程
		Thread parent = currentThread();
		this.group = g;
		//将daemon,priority属性设置为父线程的对应属性
		this.daemon = parent.isDaemon();//说明是来自于父线程的
		this.priority = parent.getPriority;//说明来自于父线程的
		this.name = name.toCharArray();//起名字以方便用jstack进行查看
		this.target = target;
		setPriority(priority);
		//将父线程的InheritableThreadLocal复制过来
		this.parent.inheritableThreadLocals=ThreadLocal.createInheritedMap(parent.inheritableThreadLocals);
		//分配一个线程ID
		tid = nextThreadID();
		
	}
