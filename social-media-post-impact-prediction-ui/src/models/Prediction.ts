export interface Prediction {
    id: number;
    description: string;
    image: string; // TODO: use File instead string
    predictedNumberOfLikes: number;
}