package semantic_check;

public class PairType extends Type {

	private BigType first;
	private BigType second;

	public PairType(Type first, Type second) {
		this.first = first;
		this.second = second;
	}

	public BigType getFirst() {
		return first;
	}

	public BigType getSecond() {
		return second;
	}

	@Override
	public boolean equals(Object other) {
		if (other instanceof SingleType && other.equals(new SingleType(EnumType.PAIR))
				|| other instanceof SingleType && other.equals(new SingleType(EnumType.NULL))) {
			return true;
		}
		if (other instanceof SingleType) {
			return false;
		} else if (other instanceof PairType) {
			PairType other2 = (PairType) other;
			return check(other2);
		}
		return false;
	}

	@Override
	public int hashCode() {
		return 0;
	}

	public String toString() {
		return "[" + first.toString() + ", " + second.toString() + "]";
	}

	@Override
	public EnumType getEnumType() {
		return EnumType.PAIR;
	}

	private boolean check(PairType obj) {
		SingleType pair = new SingleType(EnumType.PAIR);
		SingleType t_null = new SingleType(EnumType.NULL);
		if (getFirst().equals(pair) && getSecond().equals(pair)
				&& obj.getFirst().equals(t_null) && obj.getSecond().equals(t_null)) {
			return true;
		} else if (getFirst().equals(pair) && obj.getFirst().equals(t_null)) {
			return getSecond().equals(obj.second);
		} else if (getSecond().equals(pair) && obj.getSecond().equals(t_null)) {
			return getFirst().equals(obj.first);
		}
		return getFirst().equals(obj.first) || getSecond().equals(obj.second);
	}

	@Override
	public BigType getType() {
		return EnumType.ERROR;
	}

}
