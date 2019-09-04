package ebing.top.dog.service.client.impl;

import ebing.top.dog.service.utils.BinaryTree;
import ebing.top.dog.service.client.BackdoorClient;
import io.swagger.annotations.Api;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;


@Api(tags = "后门模块", description = "backdoor 模块相关接口")
@Validated
@RequestMapping("/backdoor")
@RestController
/**
 * @author dog
 */
public class BackdoorClientImpl implements BackdoorClient {

	@Override
	@GetMapping("/array")
	public String monitor(
		@RequestParam(value = "type", required = false)  String type
	) {
		if (type == "array") {
			String[] array = {"a", "b"};
			Object obj1 = array;
			return ((String[]) obj1)[0];
		}
		/**
		 * sublist 好恶心，父子互相影响
		 */
		if ("sublist".equals(type)) {
			List master = new ArrayList<Integer>();
			master.add(1);
			master.add(2);
			master.add(3);
			master.add(4);
			master.add(5);
			List sub = master.subList(0, 3);
//			master.remove(0);
//			master.add(6); // 留有这个也不行，必须3个都注释。。改变了 master 的话，sublist 也有问题。ConcurrentModificationException
//			master.clear();

			sub.clear();
			sub.add(7);
			sub.add(8);
			sub.remove(0);

			sub.forEach(i -> System.out.println(i));
			System.out.println("隔断啦");
			master.forEach(i -> System.out.println(i));
		}
		return "success";
	}

	@Override
	@PostMapping("/binary_tree")
	public String binaryTree() {
		BinaryTree bt = new BinaryTree();
		bt.createBinTree(bt.getRoot());
		System.out.println("the size of the tree is " + bt.size());
		System.out.println("the height of the tree is " + bt.height());

		System.out.println("*******(前序遍历)[ABDECF]遍历*****************");
		bt.preOrder(bt.getRoot());

		System.out.println("*******(中序遍历)[DBEACF]遍历*****************");
		bt.inOrder(bt.getRoot());

		System.out.println("*******(后序遍历)[DEBFCA]遍历*****************");
		bt.postOrder(bt.getRoot());

		System.out.println("***非递归实现****(前序遍历)[ABDECF]遍历*****************");
		bt.nonRecPreOrder(bt.getRoot());

		System.out.println("***非递归实现****(中序遍历)[DBEACF]遍历*****************");
		bt.nonRecInOrder(bt.getRoot());

		System.out.println("***非递归实现****(后序遍历)[DEBFCA]遍历*****************");
		bt.noRecPostOrder(bt.getRoot());
		return "success";
	}


}
