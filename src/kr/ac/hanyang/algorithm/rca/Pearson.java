package kr.ac.hanyang.algorithm.rca;

public class Pearson {
	public static double cor(double[] scores1, double[] scores2) {
		if(scores1 == null || scores2 == null)
			return 0;
		long start = System.currentTimeMillis();
		double result = 0;
		double sum_sq_x = 0;
		double sum_sq_y = 0;
		double sum_coproduct = 0;
		if(scores1 == null || scores2 == null || scores1.length == 0 || scores2.length == 0)
			return 0;
		double mean_x = scores1[0];
		double mean_y = scores2[0];
		for (int i = 2; i < scores1.length + 1; i += 1) {
			double sweep = Double.valueOf(i - 1) / i;
			double delta_x = scores1[i - 1] - mean_x;
			double delta_y = scores2[i - 1] - mean_y;
			sum_sq_x += delta_x * delta_x * sweep;
			sum_sq_y += delta_y * delta_y * sweep;
			sum_coproduct += delta_x * delta_y * sweep;
			mean_x += delta_x / i;
			mean_y += delta_y / i;
		}
		double pop_sd_x = (double) Math.sqrt(sum_sq_x / scores1.length);
		double pop_sd_y = (double) Math.sqrt(sum_sq_y / scores1.length);
		double cov_x_y = sum_coproduct / scores1.length;
		result = cov_x_y / (pop_sd_x * pop_sd_y) * 100.0;
//		Util.debug("", "calc PearsonCorrelation, data length:"
//				+ scores1.length + ", elapse time:"
//				+ (System.currentTimeMillis() - start) / 1000.0 + "sec");
		return result;
	}
}
