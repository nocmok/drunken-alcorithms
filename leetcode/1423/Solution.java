class Solution {
    public int maxScore(int[] cardPoints, int k) {
        int sum = 0;
        for (int i = cardPoints.length - k; i < cardPoints.length; ++i) {
            sum += cardPoints[i];
        }
        if (k == cardPoints.length) {
            return sum;
        }
        int maxScore = sum;
        int score = sum;

        int l = 0; // cards of left
        int r = k; // cards on right

        for (int i = 0; i < k; ++i) {
            score += cardPoints[l];
            score -= cardPoints[cardPoints.length - r];

            ++l;
            --r;

            maxScore = Integer.max(maxScore, score);
        }

        return maxScore;
    }
}