angular.module('mwl.calendar.docs', ['mwl.calendar', 'ngAnimate', 'ui.bootstrap', 'colorpicker.module']);
angular
    .module('mwl.calendar.docs') //you will need to declare your module with the dependencies ['mwl.calendar', 'ui.bootstrap', 'ngAnimate']
    .controller('KitchenSinkCtrl', function(moment, alert, calendarConfig) {

      var vm = this;

      vm.calendarView = 'month';
      vm.viewDate = new Date();
      vm.events = [
        {
          title: document.getElementById('paymentName').value(),
          color: calendarConfig.colorTypes.warning,
          startsAt: moment().startOf('week').subtract(2, 'days').add(8, 'hours').toDate(),
          endsAt: moment().startOf('week').add(1, 'week').add(9, 'hours').toDate()
        }, {
          title: '<i class="glyphicon glyphicon-asterisk"></i> <span class="text-primary">Another event</span>, with a <i>html</i> title',
          color: calendarConfig.colorTypes.info,
          startsAt: moment().subtract(1, 'day').toDate(),
          endsAt: moment().add(5, 'days').toDate()
        }, {
          title: 'This is a really long event title that occurs on every year',
          color: calendarConfig.colorTypes.important,
          startsAt: moment().startOf('day').add(7, 'hours').toDate(),
          endsAt: moment().startOf('day').add(19, 'hours').toDate(),
          recursOn: 'year'
        }
      ];

      vm.cellIsOpen = false;

      vm.eventClicked = function(event) {
        alert.show('Clicked', event);
      };

      vm.toggle = function($event, field, event) {
        $event.preventDefault();
        $event.stopPropagation();
        event[field] = !event[field];
      };
    });
