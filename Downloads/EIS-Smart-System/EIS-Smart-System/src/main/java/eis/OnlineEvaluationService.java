package eis;

// Service Layer
class OnlineEvaluationService extends EvaluationService {
    String platformLink;

    OnlineEvaluationService(String platformLink) {
        this.platformLink = platformLink;
    }

    @Override
    public void submitEvaluation(Student student, CourseInfo course, int rating, String comment) {
        Log.println("Submitting evaluation via: " + platformLink);
        super.submitEvaluation(student, course, rating, comment);
    }
}
