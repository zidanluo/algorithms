import java.util.Random;

public class MaximumSubArray
{
	public static void main(String[] args)
	{
		int m = 8;
		int[] arr = new int[m];
		Random r = new Random();
		for (int i = 0; i < m; i++)
		{
			arr[i] = r.nextInt(200) - 100;
		}

		//		arr[0] = 10;
		//		arr[1] = -10;
		//		arr[2] = -2;
		//		arr[3] = -2;
		//		arr[4] = -10;
		//		arr[5] = -50;
		//		arr[6] = -20;
		//		arr[7] = -1;

		for (int i = 0; i < m; i++)
		{
			System.out.print(arr[i] + " ");
		}

		SubArrayResult s = findSubArray(arr, 0, m - 1);
		System.out.println("");
		System.out.println(String.format("start: %1$d, end: %2$d, sum: %3$d", s.start, s.end, s.sum));

		s = findSubArrayForce(arr, 0, m - 1);
		System.out.println("");
		System.out.println(String.format("start: %1$d, end: %2$d, sum: %3$d", s.start, s.end, s.sum));

		s = findSubArrayLinerTime(arr, 0, m - 1);
		System.out.println("");
		System.out.println(String.format("start: %1$d, end: %2$d, sum: %3$d", s.start, s.end, s.sum));

		s = xxx(arr);
		System.out.println("");
		System.out.println(String.format("start: %1$d, end: %2$d, sum: %3$d", s.start, s.end, s.sum));

		System.out.println(MaxSubArraySum_dp(arr));

		s = Maxsum_dp(arr);
		System.out.println("");
		System.out.println(String.format("start: %1$d, end: %2$d, sum: %3$d", s.start, s.end, s.sum));
	}

	public static SubArrayResult xxx(int[] arr)
	{
		int nMax = Integer.MIN_VALUE;
		int low = 0, high = 0;
		int cur = 0; //一个指针更新子数组的左区间
		int nSum = 0;
		for (int i = 0; i < arr.length; i++)
		{
			nSum += arr[i];
			if (nSum > nMax)
			{
				nMax = nSum;
				low = cur;
				high = i;
			}
			if (nSum < 0)
			{
				cur = i + 1;
				nSum = 0;
			}
		}

		return new SubArrayResult(low, high, nMax);
	}

	public static SubArrayResult Maxsum_dp(int[] arr)
	{
		int sumj = arr[0];
		int all = arr[0];
		int start = 0, end = 0, x = 0;

		for (int i = 1; i < arr.length; ++i)
		{
			if (sumj + arr[i] > arr[i])
				sumj = sumj + arr[i];
			else
			{
				sumj = arr[i];
				x = i;
			}

			if (sumj > all)
			{
				all = sumj;
				end = i;
				start = x;
			}
		}
		return new SubArrayResult(start, end, all);
	}

	public static SubArrayResult findSubArrayLinerTime(int[] arr, int low, int high)
	{
		int sum = 0;
		int max = arr[0];
		int start = 0, end = 0;
		for (int i = 0; i < arr.length; i++)
		{
			sum = 0;
			for (int j = i + 1; j >= 0 && j < arr.length; j--)
			{
				sum += arr[j];
				if (sum >= max)
				{
					max = sum;
					end = i + 1;
					start = j;
				}
			}
		}
		return new SubArrayResult(start, end, max);
	}

	public static int MaxSubArraySum_dp(int[] arr)
	{
		int nMax = Integer.MIN_VALUE;
		int sum = 0;

		for (int i = 0; i < arr.length; i++)
		{
			if (sum >= 0)
				sum += arr[i];
			else
				sum = arr[i];
			if (sum > nMax)
				nMax = sum;
		}
		return nMax;
	}

	public static SubArrayResult findSubArrayForce(int[] arr, int low, int high)
	{
		int sum;
		int max = Integer.MIN_VALUE;
		int start = 0, end = 0;
		for (int i = 0; i < arr.length; i++)
		{
			sum = 0;
			for (int j = i; j < arr.length; j++)
			{
				sum += arr[j];
				if (sum >= max)
				{
					max = sum;
					start = i;
					end = j;
				}
			}
		}
		return new SubArrayResult(start, end, max);
	}

	public static SubArrayResult findSubArray(int[] arr, int low, int high)
	{
		if (low == high)
			return new SubArrayResult(low, high, arr[low]);
		int mid = (low + high) / 2;
		SubArrayResult left = findSubArray(arr, low, mid);
		SubArrayResult right = findSubArray(arr, mid + 1, high);
		SubArrayResult cross = findCrossSubArray(arr, low, high, mid);

		if (left.sum >= right.sum && left.sum >= cross.sum)
			return left;
		else if (right.sum >= left.sum && right.sum >= cross.sum)
			return right;
		else
			return cross;
	}

	public static SubArrayResult findCrossSubArray(int[] arr, int low, int high, int mid)
	{
		int leftSum = Integer.MIN_VALUE;
		int rightSum = Integer.MIN_VALUE;
		int sum = 0;
		int start = 0, end = 0;

		for (int i = mid; i >= low; i--)
		{
			sum += arr[i];
			if (sum > leftSum)
			{
				leftSum = sum;
				start = i;
			}
		}

		sum = 0;
		for (int i = mid + 1; i <= high; i++)
		{
			sum += arr[i];
			if (sum > rightSum)
			{
				rightSum = sum;
				end = i;
			}
		}

		return new SubArrayResult(start, end, leftSum + rightSum);
	}

	public static class SubArrayResult
	{
		public int start;
		public int end;
		public int sum;

		public SubArrayResult(int start, int end, int sum)
		{
			this.start = start;
			this.end = end;
			this.sum = sum;
		}
	}
}
