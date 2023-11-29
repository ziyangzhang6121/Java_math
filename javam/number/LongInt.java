package javam.number;

public class LongInt {
    int[] arr = new int[1024];//super long integer
	
	public LongInt(){
		for(short i = 1023;i>=0;i--){
			arr[i] = 0;
		}
	}
	
	LongInt(int n){//cast normal integer to longInt
		this();
		short i;
		int m;
		for(i = 1023;(n>=0)&&(n>0);i--){
			m = n/10;
			arr[i] = (n-10*m);
			n = m;
		}
	}
	
	public LongInt(String s){//cast String to longInt
		if(s.length()>1024){
			System.out.print("Error!!!Too long!!!");
		}
		else{
			for(int i = 0;i<s.length();i++){
				arr[1023-i] = (s.charAt(s.length()-1-i)-48);
			}
		}
	}
	
	LongInt(int[] arr){
		this();
		System.arraycopy(arr,0,this.arr,1024-arr.length,arr.length);
	}
	
	int sup(){
		int i = 0;
		for( ;i<=1023;i++){
			if(this.arr[i] == 0)continue;
			else return i;
		}
		return 1023;
	}
	
	int length(){
		return 1024-this.sup();
	}
	
	
	
	
	
	boolean isEqualTo(LongInt n){
		for(short i = 1023;i>=0;i--){
			if(this.arr[i] != n.arr[i])return false;
		}
		return true;
	}
	
	boolean isBiggerThan(LongInt n){
		for(short i = 0;i<=1023;i++){
			if(this.arr[i] > n.arr[i])return true;
			if(this.arr[i] == n.arr[i])continue;
			else return false;
		}
		return false;
	}
	
	boolean isLessThan(LongInt n){
		for(short i = 0;i<=1023;i++){
			if(this.arr[i] < n.arr[i])return true;
			if(this.arr[i] == n.arr[i])continue;
			else return false;
		}
		return false;
	}
	
	@SuppressWarnings("unused")
	boolean isNotBiggerThan(LongInt n){
		for(short i = 0;i<=1023;i++){
			if(this.arr[i] <= n.arr[i])return true;
			else return false;
		}
		return false;
	}
	
	@SuppressWarnings("unused")
	boolean isNotLessThan(LongInt n){
		for(short i = 0;i<=1023;i++){
			if(this.arr[i] >= n.arr[i])return true;
			else return false;
		}
		return false;
	}
	
	
	
	
	
	
	
	static LongInt plus(LongInt n1,LongInt n2){//plus two longInts
		int s,p = 0;
		LongInt l = new LongInt();
		for(int i = 1023;i>=0;i--){
			s = (n1.arr[i] + n2.arr[i] + p);
			p = (s/10);
			l.arr[i] = (s%10);
		}
		return l;
	}

	static LongInt plus(LongInt[] L){//plus a series of longInts
		if(L.length == 1)return L[0];
		if(L.length == 2)return plus(L[0],L[1]);
		else{
			LongInt[] LL = new LongInt[L.length-1];
			System.arraycopy(L,0,LL,0,L.length-1);
			LL[LL.length-1] = plus(L[L.length-1],LL[LL.length-1]);
			return plus(LL);
		}
	}
	
	static LongInt multiply(LongInt n1,LongInt n2){//multiply two longInts
		LongInt[] L = new LongInt[1024];
		for(int i = 1023;i>=0;i--){
			L[i] = new LongInt(0);
		}
		int s,p = 0;
		for(int i = 1023;i>=0;i--){
			for(int j = 1023;j>=0;j--){
				if(i+j-1023>=0){
					s = (n1.arr[j] * n2.arr[i] + p);
					p = (s/10);
					L[i].arr[j+i-1023] = (s%10);
				}
				else break;
			}
		}
		return plus(L);
	}
	
	static LongInt multiply(LongInt[] L){//multiply a series of longInts
		if(L.length == 1)return L[0];
		if(L.length == 2)return multiply(L[0],L[1]);
		else{
			LongInt[] LL = new LongInt[L.length-1];
			System.arraycopy(L,0,LL,0,L.length-1);
			LL[LL.length-1] = multiply(L[L.length-1],LL[LL.length-1]);
			return multiply(LL);
		}
	}
	
	static LongInt minus(LongInt n1,LongInt n2){
		if(n1.isLessThan(n2))System.out.print("Error!!!n1<n2!!!");
		else{
			LongInt l = new LongInt();
			for(int i = 1023;i>=0;i--){
				if(n1.arr[i] >= n2.arr[i])l.arr[i] = (n1.arr[i]-n2.arr[i]);
				else{
					n1.arr[i] = (n1.arr[i] + 10);
					n1.arr[i-1] = (n1.arr[i-1]-1);
					l.arr[i] = (n1.arr[i]-n2.arr[i]);
				}
			}
			return l;
		}
		return new LongInt(0);
	}
	
	
	@SuppressWarnings("unused")
	static LongInt division(LongInt n1,LongInt n2){
		int s1 = n1.sup(),s2 = n2.sup();
		int length1 = n1.length(),length2 = n2.length();
		int[] a = new int[length2];
		System.arraycopy(n1.arr,s1,a,0,length2);
		LongInt p = new LongInt(a);
		int[] result = new int[s2-s1+1];
		return new LongInt(0);
	}

	
	public static LongInt power(LongInt n1,LongInt n2){
		LongInt step = new LongInt(1);
		if(n2.isEqualTo(new LongInt(0)))return new LongInt(1);
		else return multiply(n1,power(n1,minus(n2,step)));
	}

	void print(){
		int i = 0;
		for( ;i<=1023;i++){
			if(arr[i] == 0)continue;
			break;
		}
		if(i == 1024)System.out.print(0);
		else for( ;i<=1023;i++){
			System.out.print(arr[i]);
		}	
	}
	
	public void println(){
		this.print();
		System.out.println();
	}
	
	public void numberLength() {
		int n = 0;
		int k = 0;
		for( ;k<=1023;k++) {
			if(arr[k] == 0)continue;
			break;
		}	
		if(k == 1024)System.out.print(0);
		else for( ;k<=1023;k++){
		    n++;
		}
		System.out.println(n);
	}
	
}
