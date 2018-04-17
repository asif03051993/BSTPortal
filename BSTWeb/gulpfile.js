'use strict';

var gulp = require('gulp');
var sass = require('gulp-sass');
var uglify = require('gulp-uglify');
var concat = require('gulp-concat');

var paths = {
    scripts: [
        './bstweb/**/*.js',
        '!./node_modules/**',
        '!./gulpfile.js',
        '!./bstweb/pkgs/js/all.js',
        '!./bstweb/apps.js',
        '!./bower_components/**',
        '!./server.js'
    ],
    images: './bstweb/images/*',
    css: './bstweb/styles/scss/*.scss'
};

gulp.task('style', function () {
    gulp.src('./bstweb/styles/scss/*.scss')
        .pipe(sass().on('error', sass.logError))
        .pipe(gulp.dest('./bstweb/pkgs/css/'));
});

gulp.task('script', function () {
    gulp.src(paths.scripts)
        .pipe(concat('all.js'))
        .pipe(gulp.dest('./bstweb/pkgs/js/'));
});
//watch task for bstweb
gulp.task('watch', function () {
    gulp.watch(paths.css, ['style']);
    gulp.watch(paths.scripts, ['script']);
});

//default task
gulp.task('default', ['watch', 'script','style']);