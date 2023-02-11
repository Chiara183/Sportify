package it.uniroma2.dicii.ispw.sportify.controller;

/**
 * Enumeration class that defines the different types of controllers in the application.
 */
public enum ControllerType {
    /**
     * Controller used to search for a gym.
     */
    FIND_GYM,

    /**
     * Controller used to view information about a specific gym.
     */
    GYM_INFO,

    /**
     * Controller used for the home page of the application.
     */
    HOME,

    /**
     * Controller used for the login page.
     */
    LOGIN,

    /**
     * Controller used for user registration.
     */
    SIGN_UP,

    /**
     * Controller used for gym registration.
     */
    SIGN_UP_GYM,

    /**
     * Controller used for sport quizzes.
     */
    SPORT_QUIZ,

    /**
     * Controller used for editing user information.
     */
    USER_EDIT,

    /**
     * Controller used for selecting the type of sport quiz.
     */
    SPORT_QUIZ_TYPE,

    /**
     * Controller used for selecting the environment for the sport quiz.
     */
    SPORT_QUIZ_ENV,

    /**
     * Controller used for selecting the type of user to be registered.
     */
    USER_KIND,

    /**
     * Controller used for the second step of gym registration.
     */
    SIGN_UP_GYM2,

    /**
     * Controller used for viewing detailed information about a sport.
     */
    SPORT_INFO,

    /**
     * Controller used for leaving reviews for a gym.
     */
    REVIEW_GYM,

    /**
     * Controller used for viewing courses offered by a gym.
     */
    COURSE_GYM
}
