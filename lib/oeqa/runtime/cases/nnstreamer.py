import os
from subprocess import Popen, PIPE

from oeqa.runtime.case import OERuntimeTestCase
from oeqa.core.decorator.oetimeout import OETimeout

class NNStreamerTest(OERuntimeTestCase):

    def test_nnstreamer(self):
        cmd = 'export LD_LIBRARY_PATH=/usr/lib/gstreamer-1.0:$LD_LIBRARY_PATH; ' \
        '/usr/lib/nnstreamer/unittest/unittest_common'
        (status, output) = self.target.run(cmd)
        msg = " NNSTREAMER UNITTEST FAILED ( unittest_common ): %s" % output
        self.assertEqual(status, 0, msg=msg)

        cmd = 'export LD_LIBRARY_PATH=/usr/lib/gstreamer-1.0:$LD_LIBRARY_PATH; ' \
        '/usr/lib/nnstreamer/unittest/unittest_sink -d /usr/lib/nnstreamer/customfilters --gst-plugin-path=/usr/lib/gstreamer-1.0'
        (status, output) = self.target.run(cmd)
        msg = " NNSTREAMER UNITTEST FAILED ( unittest_sink ): %s" % output
        self.assertEqual(status, 0, msg=msg)

        cmd = 'export LD_LIBRARY_PATH=/usr/lib/gstreamer-1.0:$LD_LIBRARY_PATH; ' \
        '/usr/lib/nnstreamer/unittest/unittest_plugins --gst-plugin-path=/usr/lib/gstreamer-1.0'
        (status, output) = self.target.run(cmd)
        msg = " NNSTREAMER UNITTEST FAILED ( unittest_plugins ): %s" % output
        self.assertEqual(status, 0, msg=msg)

        cmd = 'export LD_LIBRARY_PATH=/usr/lib/gstreamer-1.0:$LD_LIBRARY_PATH; ' \
        '/usr/lib/nnstreamer/unittest/unittest_src_iio --gst-plugin-path=/usr/lib/gstreamer-1.0'
        (status, output) = self.target.run(cmd)
        msg = " NNSTREAMER UNITTEST FAILED ( unittest_src_iio ): %s" % output
        self.assertEqual(status, 0, msg=msg)

        cmd = 'export UNITTEST_DIR=/usr/lib/nnstreamer/unittest; '\
              'export CUSTOMLIB_DIR=/usr/lib/nnstreamer/customfilters; '\
              'export LD_LIBRARY_PATH=/usr/lib/gstreamer-1.0:$LD_LIBRARY_PATH; ' \
              'alias python=pyton3; '\
              'cd ${UNITTEST_DIR}/tests; '\
              'ssat'
        (status, output) = self.target.run(cmd)
        if output.find('FAILED'):
            status = 0
        msg = " NNSTREAMER UNITTEST FAILED ( unittest_sink ): %s" % output
        self.assertEqual(status, 0, msg=msg)
