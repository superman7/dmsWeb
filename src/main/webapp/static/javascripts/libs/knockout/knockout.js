/*global define*/
define([
        'ko/../knockout-3.4.0',
        'ko/../knockout-es5'
    ], function(
        knockout,
        knockout_es5) {
    "use strict";

    // install the Knockout-ES5 plugin
    knockout_es5.attachToKo(knockout);
    return knockout;
});