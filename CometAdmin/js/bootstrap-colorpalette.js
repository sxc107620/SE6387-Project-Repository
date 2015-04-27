/**
 * bootstrap-colorpalette.js
 * (c) 2013~ Jiung Kang
 * Licensed under the Apache License, Version 2.0 (the "License");
 */

(function($) {
  "use strict";
  var aaColor = [
    ['#000000', '#424242', '#636363', '#9C9C94', '#CEC6CE', '#CCB1CC', '#CF99CF', '#D179D1'],
    ['#FF0000', '#FF9C00', '#FFFF00', '#00FF00', '#00FFFF', '#0000FF', '#9C00FF', '#FF00FF'],
    ['#F77C90', '#CC6A7A', '#CC6A8C', '#CC567F', '#CC56B6', '#CF3AB3', '#962F83', '#921299'],
    ['#E79C9C', '#FFC69C', '#FFE79C', '#B5D6A5', '#A5C6CE', '#9CC6EF', '#B5A5D6', '#D6A5BD'],
    ['#E76363', '#F7AD6B', '#FFD663', '#94BD7B', '#73A5AD', '#6BADDE', '#8C7BC6', '#C67BA5'],
    ['#CE0000', '#E79439', '#EFC631', '#6BA54A', '#4A7B8C', '#3984C6', '#634AA5', '#A54A7B'],
    ['#9C0000', '#B56308', '#BD9400', '#397B21', '#104A5A', '#085294', '#311873', '#731842'],
    ['#630000', '#7B3900', '#846300', '#295218', '#083139', '#003163', '#21104A', '#4A1031']
  ];
  
  var colorCode = {
    '#000000': 'Black',
    '#424242': 'Tundora',
	'#636363': 'Dove Gray',
	'#9C9C94': 'Star Dust', 
	'#CEC6CE': 'Pale Slate', 
	'#CCB1CC': 'Lily', 
	'#CF99CF': 'Lilac', 
	'#D179D1': 'Orchid', 
	'#FF0000': 'Red', 
	'#FF9C00': 'Orange Peel', 
	'#FFFF00': 'Yellow', 
	'#00FF00': 'Green', 
	'#00FFFF': 'Aqua', 
	'#0000FF': 'Blue', 
	'#9C00FF': 'Electric Violet', 
	'#FF00FF': 'Magenta',
	'#F77C90': 'Froly', 
	'#CC6A7A': 'Contessa', 
	'#CC6A8C': 'Charm', 
	'#CC567F': 'Mulberry', 
	'#CC56B6': 'Fuchsia Pink', 
	'#CF3AB3': 'Medium Red Violet', 
	'#962F83': 'Plum', 
	'#921299': 'Seance',
	'#E79C9C': 'Tonys Pink', 
	'#FFC69C': 'Peach Orange', 
	'#FFE79C': 'Cream Brulee', 
	'#B5D6A5': 'Sprout', 
	'#A5C6CE': 'Casper', 
	'#9CC6EF': 'Perano', 
	'#B5A5D6': 'Cold Purple', 
	'#D6A5BD': 'Careys Pink',
	'#E76363': 'Mandy', 
	'#F7AD6B': 'Rajah', 
	'#FFD663': 'Dandelion', 
	'#94BD7B': 'Olivine', 
	'#73A5AD': 'Gulf Stream', 
	'#6BADDE': 'Viking', 
	'#8C7BC6': 'Blue Marguerite', 
	'#C67BA5': 'Puce', 
	'#CE0000': 'Guardsman Red', 
	'#E79439': 'Fire Bush', 
	'#EFC631': 'Golden Dream', 
	'#6BA54A': 'Chelsea Cucumber', 
	'#4A7B8C': 'Smalt Blue', 
	'#3984C6': 'Boston Blue', 
	'#634AA5': 'Butterfly Bush', 
	'#A54A7B': 'Cadillac', 
	'#9C0000': 'Sangria', 
	'#B56308': 'Mai Tai', 
	'#BD9400': 'Buddha Gold', 
	'#397B21': 'Forest Green', 
	'#104A5A': 'Eden', 
	'#085294': 'Venice Blue', 
	'#311873': 'Meteorite', 
	'#731842': 'Claret', 
	'#630000': 'Rosewood', 
	'#7B3900': 'Cinnamon', 
	'#846300': 'Olive', 
	'#295218': 'Parsley', 
	'#083139': 'Tiber', 
	'#003163': 'Midnight Blue', 
	'#21104A': 'Valentino', 
	'#4A1031': 'Loulou'
};

  var createPaletteElement = function(element, _aaColor) {
    element.addClass('bootstrap-colorpalette');
    var aHTML = [];
    $.each(_aaColor, function(i, aColor){
      aHTML.push('<div>');
      $.each(aColor, function(i, sColor) {
        var sButton = ['<button type="button" class="btn-color" style="background-color:', sColor,
          '" data-value="', sColor,
          '" title="', nameColor(sColor),
          '"></button>'].join('');
        aHTML.push(sButton);
      });
      aHTML.push('</div>');
    });
    element.html(aHTML.join(''));
  };
  
  var nameColor = function(color) {
	  return colorCode[color];
  }

  var attachEvent = function(palette) {
    palette.element.on('click', function(e) {
      var welTarget = $(e.target),
          welBtn = welTarget.closest('.btn-color');

      if (!welBtn[0]) { return; }

      var value = welBtn.attr('data-value');
      palette.value = value;
      palette.element.trigger({
        type: 'selectColor',
        color: value,
        element: palette.element
      });
    });
  };

  var Palette = function(element, options) {
    this.element = element;
    createPaletteElement(element, options && options.colors || aaColor);
    attachEvent(this);
  };

  $.fn.extend({
    colorPalette : function(options) {
      this.each(function () {
        var $this = $(this),
            data = $this.data('colorpalette');
        if (!data) {
          $this.data('colorpalette', new Palette($this, options));
        }
      });
      return this;
    }
  });
})(jQuery);
