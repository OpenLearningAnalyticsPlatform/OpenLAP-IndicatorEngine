$(function () {
    var current = location.pathname;
    $('#nav-sub li a').each(function () {
        var curItem = $(this);
        if (curItem.attr('href') && curItem.attr('href').indexOf(current) !== -1) {
            curItem.closest('.collapsible-body').prev().trigger('click');
            curItem.parent().addClass('sub-active');
        }
    });
});

// (function ($) {
//     $.fn.material_select = function (callback) {
//         $(this).each(function(){
//             var $select = $(this);
//
//             if ($select.hasClass('browser-default')) {
//                 return; // Continue to next (return false breaks out of entire loop)
//             }
//
//             var multiple = $select.attr('multiple') ? true : false,
//                 lastID = $select.data('select-id'); // Tear down structure if Select needs to be rebuilt
//
//             if (lastID) {
//                 $select.parent().find('span.caret').remove();
//                 $select.parent().find('input').remove();
//
//                 $select.unwrap();
//                 $('ul#select-options-'+lastID).remove();
//             }
//
//             // If destroying the select, remove the selelct-id and reset it to it's uninitialized state.
//             if(callback === 'destroy') {
//                 $select.data('select-id', null).removeClass('initialized');
//                 return;
//             }
//
//             var uniqueID = Materialize.guid();
//             $select.data('select-id', uniqueID);
//             var wrapper = $('<div class="select-wrapper"></div>');
//             wrapper.addClass($select.attr('class'));
//             var options = $('<ul id="select-options-' + uniqueID +'" class="dropdown-content select-dropdown ' + (multiple ? 'multiple-select-dropdown' : '') + '"></ul>'),
//                 selectChildren = $select.children('option, optgroup'),
//                 valuesSelected = [],
//                 optionsHover = false;
//
//             var label = $select.find('option:selected').html() || $select.find('option:first').html() || "";
//
//             // Function that renders and appends the option taking into
//             // account type and possible image icon.
//             var appendOptionWithIcon = function(select, option, type) {
//                 // Add disabled attr if disabled
//                 var disabledClass = (option.is(':disabled')) ? 'disabled ' : '';
//
//                 // add icons
//                 var icon_url = option.data('icon');
//                 var classes = option.attr('class');
//
//                 var data_position = (option.attr('data-position') ? ' data-position="' + option.attr('data-position') + '"' : '');
//                 var data_tooltip = (option.attr('data-tooltip') ? ' data-tooltip="' + option.attr('data-tooltip') + '"' : '');
//                 var data_delay = (option.attr('data-delay') ? ' data-delay="' + option.attr('data-delay') + '"' : '');
//
//                 if (!!icon_url) {
//                     var classString = '';
//                     if (!!classes) classString = ' class="' + classes + '"';
//
//                     // Check for multiple type.
//                     if (type === 'multiple') {
//                         options.append($('<li ' + data_position + data_tooltip + data_delay + ' class="' + disabledClass + '"><img src="' + icon_url + '"' + classString + '><span><input type="checkbox"' + disabledClass + '/><label></label>' + option.html() + '</span></li>'));
//                     } else {
//                         options.append($('<li ' + data_position + data_tooltip + data_delay + ' class="' + disabledClass + '"><img src="' + icon_url + '"' + classString + '><span>' + option.html() + '</span></li>'));
//                     }
//                     return true;
//                 }
//
//                 // Check for multiple type.
//                 if (type === 'multiple') {
//                     options.append($('<li ' + data_position + data_tooltip + data_delay + ' class="' + disabledClass + '"><span><input type="checkbox"' + disabledClass + '/><label></label>' + option.html() + '</span></li>'));
//                 } else {
//                     //options.append($('<li class="' + disabledClass + '"><span>' + option.html() + '</span></li>'));
//                     options.append($('<li ' + data_position + data_tooltip + data_delay + ' class="' + disabledClass + '" ><span>' + option.html() + '</span></li>'));
//                 }
//             };
//
//             /* Create dropdown structure. */
//             if (selectChildren.length) {
//                 selectChildren.each(function() {
//                     if ($(this).is('option')) {
//                         // Direct descendant option.
//                         if (multiple) {
//                             appendOptionWithIcon($select, $(this), 'multiple');
//
//                         } else {
//                             appendOptionWithIcon($select, $(this));
//                         }
//                     } else if ($(this).is('optgroup')) {
//                         // Optgroup.
//                         var selectOptions = $(this).children('option');
//                         options.append($('<li class="optgroup"><span>' + $(this).attr('label') + '</span></li>'));
//
//                         selectOptions.each(function() {
//                             appendOptionWithIcon($select, $(this));
//                         });
//                     }
//                 });
//             }
//
//             options.find('li:not(.optgroup)').each(function (i) {
//                 if ($(this).attr('data-tooltip'))
//                     $(this).tooltip();
//
//                 $(this).click(function (e) {
//                     // Check if option element is disabled
//                     if (!$(this).hasClass('disabled') && !$(this).hasClass('optgroup')) {
//                         var selected = true;
//
//                         if (multiple) {
//                             $('input[type="checkbox"]', this).prop('checked', function(i, v) { return !v; });
//                             selected = toggleEntryFromArray(valuesSelected, $(this).index(), $select);
//                             $newSelect.trigger('focus');
//                         } else {
//                             options.find('li').removeClass('active');
//                             $(this).toggleClass('active');
//                             $newSelect.val($(this).text());
//                         }
//
//                         activateOption(options, $(this));
//                         $select.find('option').eq(i).prop('selected', selected);
//                         // Trigger onchange() event
//                         $select.trigger('change');
//                         if (typeof callback !== 'undefined') callback();
//                     }
//
//                     e.stopPropagation();
//                 });
//             });
//
//             // Wrap Elements
//             $select.wrap(wrapper);
//             // Add Select Display Element
//             var dropdownIcon = $('<span class="caret">&#9660;</span>');
//             if ($select.is(':disabled'))
//                 dropdownIcon.addClass('disabled');
//
//             // escape double quotes
//             var sanitizedLabelHtml = label.replace(/"/g, '&quot;');
//
//             var $newSelect = $('<input type="text" class="select-dropdown" readonly="true" ' + (($select.is(':disabled')) ? 'disabled' : '') + ' data-activates="select-options-' + uniqueID +'" value="'+ sanitizedLabelHtml +'"/>');
//             $select.before($newSelect);
//             $newSelect.before(dropdownIcon);
//
//             $newSelect.after(options);
//             // Check if section element is disabled
//             if (!$select.is(':disabled')) {
//                 $newSelect.dropdown({'hover': false, 'closeOnClick': false});
//             }
//
//             // Copy tabindex
//             if ($select.attr('tabindex')) {
//                 $($newSelect[0]).attr('tabindex', $select.attr('tabindex'));
//             }
//
//             $select.addClass('initialized');
//
//             $newSelect.on({
//                 'focus': function (){
//                     if ($('ul.select-dropdown').not(options[0]).is(':visible')) {
//                         $('input.select-dropdown').trigger('close');
//                     }
//                     if (!options.is(':visible')) {
//                         $(this).trigger('open', ['focus']);
//                         var label = $(this).val();
//                         var selectedOption = options.find('li').filter(function() {
//                             return $(this).text().toLowerCase() === label.toLowerCase();
//                         })[0];
//                         activateOption(options, selectedOption);
//                     }
//                 },
//                 'click': function (e){
//                     e.stopPropagation();
//                 }
//             });
//
//             $newSelect.on('blur', function() {
//                 if (!multiple) {
//                     $(this).trigger('close');
//                 }
//                 options.find('li.selected').removeClass('selected');
//             });
//
//             options.hover(function() {
//                 optionsHover = true;
//             }, function () {
//                 optionsHover = false;
//             });
//
//             $(window).on({
//                 'click': function () {
//                     multiple && (optionsHover || $newSelect.trigger('close'));
//                 }
//             });
//
//             // Add initial multiple selections.
//             if (multiple) {
//                 $select.find("option:selected:not(:disabled)").each(function () {
//                     var index = $(this).index();
//
//                     toggleEntryFromArray(valuesSelected, index, $select);
//                     options.find("li").eq(index).find(":checkbox").prop("checked", true);
//                 });
//             }
//
//             // Make option as selected and scroll to selected position
//             activateOption = function(collection, newOption) {
//                 if (newOption) {
//                     collection.find('li.selected').removeClass('selected');
//                     var option = $(newOption);
//                     option.addClass('selected');
//                     options.scrollTo(option);
//                 }
//             };
//
//             // Allow user to search by typing
//             // this array is cleared after 1 second
//             var filterQuery = [],
//                 onKeyDown = function(e){
//                     // TAB - switch to another input
//                     if(e.which == 9){
//                         $newSelect.trigger('close');
//                         return;
//                     }
//
//                     // ARROW DOWN WHEN SELECT IS CLOSED - open select options
//                     if(e.which == 40 && !options.is(':visible')){
//                         $newSelect.trigger('open');
//                         return;
//                     }
//
//                     // ENTER WHEN SELECT IS CLOSED - submit form
//                     if(e.which == 13 && !options.is(':visible')){
//                         return;
//                     }
//
//                     e.preventDefault();
//
//                     // CASE WHEN USER TYPE LETTERS
//                     var letter = String.fromCharCode(e.which).toLowerCase(),
//                         nonLetters = [9,13,27,38,40];
//                     if (letter && (nonLetters.indexOf(e.which) === -1)) {
//                         filterQuery.push(letter);
//
//                         var string = filterQuery.join(''),
//                             newOption = options.find('li').filter(function() {
//                                 return $(this).text().toLowerCase().indexOf(string) === 0;
//                             })[0];
//
//                         if (newOption) {
//                             activateOption(options, newOption);
//                         }
//                     }
//
//                     // ENTER - select option and close when select options are opened
//                     if (e.which == 13) {
//                         var activeOption = options.find('li.selected:not(.disabled)')[0];
//                         if(activeOption){
//                             $(activeOption).trigger('click');
//                             if (!multiple) {
//                                 $newSelect.trigger('close');
//                             }
//                         }
//                     }
//
//                     // ARROW DOWN - move to next not disabled option
//                     if (e.which == 40) {
//                         if (options.find('li.selected').length) {
//                             newOption = options.find('li.selected').next('li:not(.disabled)')[0];
//                         } else {
//                             newOption = options.find('li:not(.disabled)')[0];
//                         }
//                         activateOption(options, newOption);
//                     }
//
//                     // ESC - close options
//                     if (e.which == 27) {
//                         $newSelect.trigger('close');
//                     }
//
//                     // ARROW UP - move to previous not disabled option
//                     if (e.which == 38) {
//                         newOption = options.find('li.selected').prev('li:not(.disabled)')[0];
//                         if(newOption)
//                             activateOption(options, newOption);
//                     }
//
//                     // Automaticaly clean filter query so user can search again by starting letters
//                     setTimeout(function(){ filterQuery = []; }, 1000);
//                 };
//
//             $newSelect.on('keydown', onKeyDown);
//         });
//
//         function toggleEntryFromArray(entriesArray, entryIndex, select) {
//             var index = entriesArray.indexOf(entryIndex),
//                 notAdded = index === -1;
//
//             if (notAdded) {
//                 entriesArray.push(entryIndex);
//             } else {
//                 entriesArray.splice(index, 1);
//             }
//
//             select.siblings('ul.dropdown-content').find('li').eq(entryIndex).toggleClass('active');
//
//             // use notAdded instead of true (to detect if the option is selected or not)
//             select.find('option').eq(entryIndex).prop('selected', notAdded);
//             setValueToInput(entriesArray, select);
//
//             return notAdded;
//         }
//
//         function setValueToInput(entriesArray, select) {
//             var value = '';
//
//             for (var i = 0, count = entriesArray.length; i < count; i++) {
//                 var text = select.find('option').eq(entriesArray[i]).text();
//
//                 i === 0 ? value += text : value += ', ' + text;
//             }
//
//             if (value === '') {
//                 value = select.find('option:disabled').eq(0).text();
//             }
//
//             select.siblings('input.select-dropdown').val(value);
//         }
//     };
// }( jQuery ));

(function ($) {
    $.fn.tooltip = function (options) {
        var timeout = null,
            margin = 5;

        // Defaults
        var defaults = {
            delay: 350
        };

        // Remove tooltip from the activator
        if (options === "remove") {
            this.each(function(){
                $('#' + $(this).attr('data-tooltip-id')).remove();
                $(this).off('mouseenter.tooltip mouseleave.tooltip');
            });
            return false;
        }

        options = $.extend(defaults, options);


        return this.each(function(){
            var tooltipId = Materialize.guid();
            var origin = $(this);
            origin.attr('data-tooltip-id', tooltipId);

            // Create Text span
            var tooltip_text = $('<div></div>').html(origin.attr('data-tooltip'));

            // Create tooltip
            var newTooltip = $('<div></div>');
            newTooltip.addClass('material-tooltip').append(tooltip_text)
                .appendTo($('body'))
                .attr('id', tooltipId);

            var backdrop = $('<div></div>').addClass('backdrop');
            backdrop.appendTo(newTooltip);
            backdrop.css({ top: 0, left:0 });


            //Destroy previously binded events
            origin.off('mouseenter.tooltip mouseleave.tooltip');
            // Mouse In
            var started = false, timeoutRef;
            origin.on({
                'mouseenter.tooltip': function(e) {
                    var tooltip_delay = origin.attr('data-delay');
                    tooltip_delay = (tooltip_delay === undefined || tooltip_delay === '') ?
                        options.delay : tooltip_delay;
                    timeoutRef = setTimeout(function(){
                        started = true;
                        newTooltip.velocity('stop');
                        backdrop.velocity('stop');
                        newTooltip.css({ display: 'block', left: '0px', top: '0px' });

                        // Set Tooltip text
                        newTooltip.children('span').text(origin.attr('data-tooltip'));

                        // Tooltip positioning
                        var originWidth = origin.outerWidth();
                        var originHeight = origin.outerHeight();
                        var tooltipPosition =  origin.attr('data-position');
                        var tooltipHeight = newTooltip.outerHeight();
                        var tooltipWidth = newTooltip.outerWidth();
                        var tooltipVerticalMovement = '0px';
                        var tooltipHorizontalMovement = '0px';
                        var scale_factor = 8;
                        var targetTop, targetLeft, newCoordinates;

                        if (tooltipPosition === "top") {
                            // Top Position
                            targetTop = origin.offset().top - tooltipHeight - margin;
                            targetLeft = origin.offset().left + originWidth/2 - tooltipWidth/2;
                            newCoordinates = repositionWithinScreen(targetLeft, targetTop, tooltipWidth, tooltipHeight);

                            tooltipVerticalMovement = '-10px';
                            backdrop.css({
                                borderRadius: '14px 14px 0 0',
                                transformOrigin: '50% 90%',
                                marginTop: tooltipHeight,
                                marginLeft: (tooltipWidth/2) - (backdrop.width()/2)
                            });
                        }
                        // Left Position
                        else if (tooltipPosition === "left") {
                            targetTop = origin.offset().top + originHeight/2 - tooltipHeight/2;
                            targetLeft =  origin.offset().left - tooltipWidth - margin;
                            newCoordinates = repositionWithinScreen(targetLeft, targetTop, tooltipWidth, tooltipHeight);

                            tooltipHorizontalMovement = '-10px';
                            backdrop.css({
                                width: '14px',
                                height: '14px',
                                borderRadius: '14px 0 0 14px',
                                transformOrigin: '95% 50%',
                                marginTop: tooltipHeight/2,
                                marginLeft: tooltipWidth
                            });
                        }
                        // Right Position
                        else if (tooltipPosition === "right") {
                            targetTop = origin.offset().top + originHeight/2 - tooltipHeight/2;
                            targetLeft = origin.offset().left + originWidth + margin;
                            newCoordinates = repositionWithinScreen(targetLeft, targetTop, tooltipWidth, tooltipHeight);

                            tooltipHorizontalMovement = '+10px';
                            backdrop.css({
                                width: '14px',
                                height: '14px',
                                borderRadius: '0 14px 14px 0',
                                transformOrigin: '5% 50%',
                                marginTop: tooltipHeight/2,
                                marginLeft: '0px'
                            });
                        }
                        else {
                            // Bottom Position
                            targetTop = origin.offset().top + origin.outerHeight() + margin;
                            targetLeft = origin.offset().left + originWidth/2 - tooltipWidth/2;
                            newCoordinates = repositionWithinScreen(targetLeft, targetTop, tooltipWidth, tooltipHeight);
                            tooltipVerticalMovement = '+10px';
                            backdrop.css({
                                marginLeft: (tooltipWidth/2) - (backdrop.width()/2)
                            });
                        }

                        // Set tooptip css placement
                        newTooltip.css({
                            top: newCoordinates.y,
                            left: newCoordinates.x
                        });

                        // Calculate Scale to fill
                        scale_factor = tooltipWidth / 8;
                        if (scale_factor < 8) {
                            scale_factor = 8;
                        }
                        if (tooltipPosition === "right" || tooltipPosition === "left") {
                            scale_factor = tooltipWidth / 10;
                            if (scale_factor < 6)
                                scale_factor = 6;
                        }

                        newTooltip.velocity({ marginTop: tooltipVerticalMovement, marginLeft: tooltipHorizontalMovement}, { duration: 350, queue: false })
                            .velocity({opacity: 1}, {duration: 300, delay: 50, queue: false});
                        backdrop.css({ display: 'block' })
                            .velocity({opacity:1},{duration: 55, delay: 0, queue: false})
                            .velocity({scale: scale_factor}, {duration: 300, delay: 0, queue: false, easing: 'easeInOutQuad'});


                    }, tooltip_delay); // End Interval

                    // Mouse Out
                },
                'mouseleave.tooltip': function(){
                    // Reset State
                    started = false;
                    clearTimeout(timeoutRef);

                    // Animate back
                    setTimeout(function() {
                        if (started != true) {
                            newTooltip.velocity({
                                opacity: 0, marginTop: 0, marginLeft: 0}, { duration: 225, queue: false});
                            backdrop.velocity({opacity: 0, scale: 1}, {
                                duration:225,
                                queue: false,
                                complete: function(){
                                    backdrop.css('display', 'none');
                                    newTooltip.css('display', 'none');
                                    started = false;}
                            });
                        }
                    },225);
                }
            });
        });
    };

    var repositionWithinScreen = function(x, y, width, height) {
        var newX = x
        var newY = y;

        if (newX < 0) {
            newX = 4;
        } else if (newX + width > window.innerWidth) {
            newX -= newX + width - window.innerWidth;
        }

        if (newY < 0) {
            newY = 4;
        } else if (newY + height > window.innerHeight + $(window).scrollTop) {
            newY -= newY + height - window.innerHeight;
        }

        return {x: newX, y: newY};
    };

    $(document).ready(function(){
        $('.tooltipped').tooltip();
    });
}( jQuery ));