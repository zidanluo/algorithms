import java.util.Random;

/**
 * Created by luoyp on 2016/11/23.
 */
public class BoyerMoore
{
	private static Integer[] calculateBad(String pattern)
	{

		Integer[] bad = new Integer[256];
		for (int i = 0; i < bad.length; i++)
		{
			bad[i] = -1;
		}
		for (int j = pattern.length() - 2; j >= 0; j--)
		{
			if (bad[pattern.charAt(j)] == -1)
			{
				// 模式字符串最右边出现的位置
				bad[pattern.charAt(j)] = j;
			}
		}

		StringBuffer pa = new StringBuffer();
		StringBuffer ba = new StringBuffer();

		for (int i = 0; i < bad.length; i++)
		{
			if (bad[i] != -1)
			{
				pa.append((char) i).append(" ");
				ba.append(bad[i]).append(" ");
			}
		}
		System.out.println("");
		System.out.println(pa.append(" : pattern"));
		System.out.println(ba.append(" : bad"));

		return bad;
	}

	private static Integer[] calculateBadGood(String pattern)
	{
		int m = pattern.length();
		Integer[] good = new Integer[m];

		Integer[] suff = suffix(pattern);

		//Case3
		for (int i = 0; i < m; i++)
		{
			good[i] = m;
			if (i == m - 1)
				good[i] = 1;
		}

		//Case2
		int j = 0;
		for (int i = m - 1; i >= 0; i--)
		{
			if (suff[i] == i + 1)
			{
				for (; j < m - 1 - i; j++)
				{
					if (good[j] == m)
						good[j] = m - 1 - i;
				}
			}
		}
		System.out.println("");

		//Case1
		for (int i = 0; i < m - 2; i++)
		{
			if (suff[i] > 0)
			{
				//				System.out.print(m - 1 - suff[i]);
				good[m - 1 - suff[i]] = m - 1 - i;
				//				System.out.println(" " + (m - 1 - i));
			}

		}

		StringBuffer su = new StringBuffer();
		StringBuffer go = new StringBuffer();
		StringBuffer pa = new StringBuffer();
		for (int c = 0; c < suff.length; c++)
		{
			System.out.print(c + " ");
			su.append(suff[c]).append(" ");
			pa.append(pattern.charAt(c)).append(" ");
			go.append(good[c]).append(" ");
		}
		System.out.println("");
		System.out.println(pa.append(" : pattern"));
		System.out.println(su.append(" : suff"));
		System.out.println(go.append(" : good"));

		return good;
	}

	private static Integer[] suffix(String pattern)
	{
		int m = pattern.length();
		Integer[] suff = new Integer[pattern.length()];
		int j;
		suff[m - 1] = m;
		for (int i = m - 2; i >= 0; i--)
		{
			j = i;
			while (j >= 0 && pattern.charAt(j) == pattern.charAt(m - 1 - i + j))
				j--;
			suff[i] = i - j;
		}

		return suff;
	}

	public static void main(String[] args)
	{
		int n = 300;
		char[] charText = new char[n];
		Random r = new Random();
		for (int x = 0; x < 300; x++)
		{
			int y = r.nextInt(4);
			if (y == 0)
				charText[x] = 'a';
			else if (y == 1)
				charText[x] = 'b';
			else if (y == 2)
				charText[x] = 'c';
			else if (y == 3)
				charText[x] = 'd';
		}

//		charText[0] = 'v';
//		charText[1] = 'v';
//		charText[2] = 'a';
//		charText[3] = 'a';
//		charText[4] = 'b';

		String text = new String(charText);
		String pattern = "bdabab";

		System.out.println(text);
		System.out.print(String.format(pattern));

		Integer[] bad = calculateBad(pattern);
		Integer[] good = calculateBadGood(pattern);

		System.out.println("");

		int j = 0;
		int i;

		int m = pattern.length();
		//		int n = text.length();
		while (j < n - m)
		{
			boolean b;
			i = m - 1;
			while (true)
			{
				if (i < 0)
				{
					System.out.println("");
					System.out.println(j);
					System.out.println(text);
					System.out.print(String.format("%1$" + (j + m) + "s", pattern));
					return;
				}
				if (b = pattern.charAt(i) == text.charAt(i + j))
					i--;
				else
					break;
			}

			if (!b)
			{
				System.out.println(String.format("i:%1$d, j:%2$3s, good:%3$d, bad:%4$2s, pattern[i]:%5$s, text[i+j]:%6$s", i, j, good[i], i - bad[text.charAt(i + j)], pattern.charAt(i), text.charAt(i + j)));
				j += Math.max(good[i], i - bad[text.charAt(i + j)]);
			}

		}
		System.out.println("no");
	}
}
