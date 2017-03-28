/**
 * Copyright © 2000-2016 西安航天天绘数据技术有限公司地理信息与制图室所有
 */
/**
 * @工程: gisplatform
 * @包名:
 * @描述:
 * @作者: 巩志远(iamlgong@163.com)
 * @版本: V1.0
 * @时间: 2016/4/29 13:56
 */

(function () {
    "use strict";
    $.browser = {};
    $.browser.mozilla = /firefox/.test(navigator.userAgent.toLowerCase());
    $.browser.webkit = /webkit/.test(navigator.userAgent.toLowerCase());
    $.browser.opera = /opera/.test(navigator.userAgent.toLowerCase());
    $.browser.msie = /msie/.test(navigator.userAgent.toLowerCase());

    if ($.browser.msie) {
        //IE8及以下
        if (!$.support.leadingWhitespace) {
            require(['html5shiv', 'respond']);
        }
    }


    $(".left-side").niceScroll({
        styler: "fb",
        cursorcolor: "#65cea7",
        cursorwidth: '3',
        cursorborderradius: '0px',
        background: '#424f63',
        spacebarenabled: false,
        cursorborder: '0'
    });

    var lefSizeNiceScroll = $(".left-side").getNiceScroll();
    if ($('body').hasClass('left-side-collapsed')) {
        lefSizeNiceScroll.hide();
    }


    // Toggle Left Menu
    jQuery('.menu-list > a').click(function () {

        var parent = jQuery(this).parent();
        var sub = parent.find('> ul');

        if (!jQuery('body').hasClass('left-side-collapsed')) {
            if (sub.is(':visible')) {
                sub.slideUp(200, function () {
                    parent.removeClass('nav-active');
                    jQuery('.main-content').css({height: ''});
                    mainContentHeightAdjust();
                });
            } else {
                visibleSubMenuClose();
                parent.addClass('nav-active');
                sub.slideDown(200, function () {
                    mainContentHeightAdjust();
                });
            }
        }
        return false;
    });

    function visibleSubMenuClose() {
        jQuery('.menu-list').each(function () {
            var t = jQuery(this);
            if (t.hasClass('nav-active')) {
                t.find('> ul').slideUp(200, function () {
                    t.removeClass('nav-active');
                });
            }
        });
    }

    function mainContentHeightAdjust() {
        // Adjust main content height
        var docHeight = jQuery(document).height();
        if (docHeight > jQuery('.main-content').height())
            jQuery('.main-content').height(docHeight);
    }

    //  class add mouse hover
    jQuery('.custom-nav > li').hover(function () {
        jQuery(this).addClass('nav-hover');
    }, function () {
        jQuery(this).removeClass('nav-hover');
    });


    // Menu Toggle
    jQuery('.toggle-btn').click(function () {
        lefSizeNiceScroll.hide();

        if ($('body').hasClass('left-side-collapsed')) {
            lefSizeNiceScroll.hide();
        }
        var body = jQuery('body');
        var bodyposition = body.css('position');

        if (bodyposition != 'relative') {

            if (!body.hasClass('left-side-collapsed')) {
                body.addClass('left-side-collapsed');
                jQuery('.custom-nav ul').attr('style', '');

                jQuery(this).addClass('menu-collapsed');

            } else {
                body.removeClass('left-side-collapsed chat-view');
                jQuery('.custom-nav li.active ul').css({display: 'block'});

                jQuery(this).removeClass('menu-collapsed');

            }
        } else {

            if (body.hasClass('left-side-show'))
                body.removeClass('left-side-show');
            else
                body.addClass('left-side-show');

            mainContentHeightAdjust();
        }

    });


    searchform_reposition();

    jQuery(window).resize(function () {

        if (jQuery('body').css('position') == 'relative') {

            jQuery('body').removeClass('left-side-collapsed');

        } else {

            jQuery('body').css({left: '', marginRight: ''});
        }

        searchform_reposition();

    });

    function searchform_reposition() {
        if (jQuery('.searchform').css('position') == 'relative') {
            jQuery('.searchform').insertBefore('.left-side-inner .logged-user');
        } else {
            jQuery('.searchform').insertBefore('.menu-right');
        }
    }

    // panel collapsible
    $('.panel .tools .fa').click(function () {
        var el = $(this).parents(".panel").children(".panel-body");
        if ($(this).hasClass("fa-chevron-down")) {
            $(this).removeClass("fa-chevron-down").addClass("fa-chevron-up");
            el.slideUp(200);
        } else {
            $(this).removeClass("fa-chevron-up").addClass("fa-chevron-down");
            el.slideDown(200);
        }
    });

    $('.todo-check label').click(function () {
        $(this).parents('li').children('.todo-title').toggleClass('line-through');
    });

    $(document).on('click', '.todo-remove', function () {
        $(this).closest("li").remove();
        return false;
    });

    // panel close
    $('.panel .tools .fa-times').click(function () {
        $(this).parents(".panel").parent().remove();
    });

    // tool tips

    $('.tooltips').tooltip();
    // popovers

    $('.popovers').popover();

    //功能项
    var lis = $('.left-side-inner li');
    //查找与功能相关的页面项
    var url = window.location.href;
    var items = $(lis).filter(function (index, li) {
        var a = $(li).find('a');
        return url.indexOf($(a).attr('href')) > -1;
    });
    if (items.length > 0) {
        lis.removeClass('active');
        items.addClass('active');
    }
    resizeWrapperLayout();
    $(window).resize(function () {
        resizeWrapperLayout();
    });
    $('.wrapper').resize(function () {
        alert('resize');
    });
})(jQuery);
var wrapperScroll = null;
function resizeWrapperLayout() {
    var height = $('.main-content').innerHeight() - $('.header-section').height() - $('.page-heading').height();
    var $wrapper = $('.wrapper');
    var children = $wrapper.children();
    var wholeHeight = 0;
    children.each(function (i, item) {
        wholeHeight += $(item).innerHeight();
    });
    $wrapper.height(height);
    if (wholeHeight > height){
        if (wrapperScroll == null) {
            wrapperScroll = $wrapper.niceScroll({
                styler: "fb"
                , cursorcolor: "#65cea7"
                , cursorwidth: '6'
                , cursorborderradius: '0px'
                , background: '#424f63'
                , spacebarenabled: false
                , cursorborder: '0'
                , zindex: '1000'
                , autohidemode: false
            });
        }
        wrapperScroll.resize();
    }
}