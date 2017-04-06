package com.whale.demo.mq;


//二叉查找树
public class BinaryNodeTest<T> {
    private BinaryNode<T> root;
    private T element;
    int i=0;
    public BinaryNodeTest(){
        this(null);
    }
    public BinaryNodeTest(T element){
        this.root=null;
        this.element=element;
    }
    //判断树是否为空
    private boolean isEmpty(){
        return this.root==null;
    }
    //插入数据
    public void insert(T t)
    {
        this.root= insert(t,this.root);
    }

    private BinaryNode<T> insert(T theelement,BinaryNode<T> root){
        i++;
        if(root==null){
            System.out.println("第"+i+"步:  "+"在插入数据的位置创建一个节点 该节点的值是： "+theelement);
            return new BinaryNode<T>(theelement,null,null);
        }
        int compareResult=((Integer) theelement).compareTo((Integer) root.element);
        if(compareResult<0){
            System.out.println("第"+i+"步:  "+"将要插入的值为"+theelement+"比节点"+root.element+"小，将该值插入节点的左孩子");
            root.left=insert(theelement,root.left);
        }
        else if(compareResult>0){
            System.out.println("第"+i+"步:  "+"将要插入的值为"+theelement+"比节点"+root.element+"大，将该值插入节点的右孩子");
            root.right=insert(theelement,root.right);
        }
        else
            System.out.println("第"+i+"步:  "+"存在等值的节点所以没有插入该节点 该节点的数据累加值为"+(root.k+1));
        return root;
    }
    //打印树的节点
    private void printTree(){
        if(isEmpty()){
            System.out.println("空树");
        }
        else
            printTree(root);
    }
    private void printTree(BinaryNode<T> root){
        if(root!=null){
            printTree(root.left);
            System.out.println(root.element);
            printTree(root.right);
        }
    }
    //查找节点的值是否存在
    public boolean contains(T t){
        return contains(t,root);
    }
    private boolean contains(T t,BinaryNode<T> root){
        if(root==null)
            return false;
        int compareResult=((Integer) t).compareTo((Integer)root.element);
        if(compareResult<0)
            return contains(t,root.left);
        else if(compareResult>0)
            return contains(t,root.right);
        else
            return true;
    }
    //找出最小的值
    private Integer findMin(){
        return (Integer)findMin(root).element;
    }
    private BinaryNode<T> findMin(BinaryNode<T> root){
        if(root==null)return null;
        else if(root.left==null)
            return root;

        return findMin(root.left);
    }
    //找出最大的值
    private Integer findMax(){
        return (Integer)findMax(root).element;
    }
    private BinaryNode<T> findMax(BinaryNode<T> root){
        if(root==null)
            return null;
        else if(root.right==null)
            return root;
        else
            return findMax(root.right);
    }
    //删除一个节点
    private void remove(T t){
        this.root=remove(t,root);
    }
    private BinaryNode<T> remove(T t,BinaryNode<T> root){
        if(root==null){
            return root;
        }
        int compareResult=((Integer)t).compareTo((Integer)root.element);
        if(compareResult<0){
            root.left=remove(t,root.left );
        }
        else if(compareResult>0){
            root.right=remove(t,root.right);
        }
        else if(root.left!=null&&root.right!=null){
            root.element=findMin(root.right).element;//找出右子树最小值
            root.right=remove((T)root.element,root.right);
        }
        else
            root=(root.left!=null)?root.left:root.right;
        return root;
    }
    public static void main(String[] args) {
        // TODO Auto-generated method stub
        BinaryNodeTest<Integer> searchTree=new BinaryNodeTest<Integer>();
        searchTree.insert(8);
        searchTree.insert(9);
        searchTree.insert(10);
        searchTree.insert(2);
        searchTree.insert(3);
        searchTree.insert(7);
        searchTree.printTree();
        System.out.println("是否含有6： "+searchTree.contains(6));
        System.out.println("查找到最小的是： "+searchTree.findMin());
        System.out.println("查找到最大的是： "+searchTree.findMax());
        searchTree.remove(9);
        searchTree.printTree();
    }

}

//定义二叉树的节点
class BinaryNode<T>{
    int k=1;//累计相同节点的个数
    Object element;
    BinaryNode<T> left;
    BinaryNode<T> right;
    public BinaryNode(T theelement){
        this(theelement,null,null);
    };
    public BinaryNode(T theelement,BinaryNode<T> left,BinaryNode<T> right){
        element=theelement;
        this.left=left;
        this.right=right;
    };


}


