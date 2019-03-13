package ebing.top.dog.service.client.impl;

import ebing.top.dog.service.Utils.BinaryTree;
import ebing.top.dog.service.client.BackdoorClient;
import io.swagger.annotations.Api;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@Api(tags = "后门模块", description = "backdoor 模块相关接口")
@Validated
@RequestMapping("/backdoor")
@RestController
/**
 * @author dog
 */
public class BackdoorClientImpl implements BackdoorClient {

	@Override
	@GetMapping("/monitor")
	public String monitor() {
		return "hello world";
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
