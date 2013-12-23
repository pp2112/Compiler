package generator;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import code.Register;
import code.Variable;

public class RegList implements Variable {

	List<Register> list = new ArrayList<Register>();

	public RegList() {
	}

	public RegList(Register r1) {
		list.add(r1);
	}

	public RegList(Register r1, Register r2) {
		list.add(r1);
		list.add(r2);
	}

	public void addRegister(Register r1) {
		list.add(r1);
	}

	public Register getFreeReg() {
		for (Register reg : list) {
			if (!reg.checkInUse()) {
				return reg;
			}
		}
		System.out.println("No free registers!");
		return null;
	}

	public void addAll() {
		list = Arrays.asList(Register.r0, Register.r1, Register.r2, Register.r3,
				Register.r4, Register.r5, Register.r6, Register.r7, Register.r8,
				Register.r9, Register.r10, Register.r11, Register.r12, Register.sp,
				Register.lr, Register.pc);
	}

	public String toString() {
		String result = "{";
		for (Iterator<Register> iterator = list.iterator(); iterator.hasNext();){
			result += iterator.next().toString();
			if (iterator.hasNext())
				result += ", ";
		}
		return result + "}";
	}

}
